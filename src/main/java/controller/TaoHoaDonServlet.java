package controller;

import dao.*;
import entity.*;
import util.JDBC;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/taohoadon")
public class TaoHoaDonServlet extends HttpServlet {

    DoUongDAOImpl doUongDAO = new DoUongDAOImpl();
    LoaiDoUongDAOImpl loaiDAO = new LoaiDoUongDAOImpl();

    // ================= GET =================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("keyword");
        String maLoai = request.getParameter("maLoai");

        List<DoUong> list;

        // SEARCH
        if (keyword != null && !keyword.trim().isEmpty()) {
            list = doUongDAO.findByTenDoUong(keyword);
        }
        // FILTER
        else if (maLoai != null && !maLoai.isEmpty()) {
            list = doUongDAO.findByMaLoai(Integer.parseInt(maLoai));
        }
        // ALL
        else {
            list = doUongDAO.findAll();
        }

        request.setAttribute("listDoUong", list);
        request.setAttribute("loaiList", loaiDAO.selectAll());

        request.getRequestDispatcher("taohoadon.jsp").forward(request, response);
    }

    // ================= POST =================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        List<GioHang> cart = (List<GioHang>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        String action = request.getParameter("action");

        // ================= THÊM MÓN =================
        if ("them".equals(action)) {

            int maDoUong = Integer.parseInt(request.getParameter("maDoUong"));
            DoUong d = doUongDAO.findById(maDoUong);

            boolean tonTai = false;

            for (GioHang g : cart) {
                if (g.getMaDoUong() == maDoUong) {
                    g.setSoLuong(g.getSoLuong() + 1);
                    tonTai = true;
                    break;
                }
            }

            if (!tonTai) {
                cart.add(new GioHang(
                        d.getMaDoUong(),
                        d.getTenDoUong(),
                        d.getGiaTien(), // ⚠️ nhớ đúng tên field
                        1
                ));
            }
        }

        // ================= XÓA TẤT CẢ =================
        else if ("xoaAll".equals(action)) {
            cart.clear();
        }

        // ================= THANH TOÁN =================
        else if ("thanhtoan".equals(action)) {

            if (!cart.isEmpty()) {

                int tong = 0;
                for (GioHang g : cart) {
                    tong += g.getThanhTien();
                }

                // 🔥 copy để show popup (tránh bị clear)
                List<GioHang> hoaDonTam = new ArrayList<>(cart);

                try (Connection conn = JDBC.getConnection()) {

                    // INSERT HOADON
                    String sqlHD = "INSERT INTO HoaDon(maNguoiDung, trangThai, tongTien) VALUES (?,?,?)";
                    PreparedStatement ps = conn.prepareStatement(sqlHD, Statement.RETURN_GENERATED_KEYS);

                    ps.setInt(1, 1);
                    ps.setBoolean(2, true);
                    ps.setInt(3, tong);

                    ps.executeUpdate();

                    ResultSet rs = ps.getGeneratedKeys();
                    int maHD = 0;

                    if (rs.next()) {
                        maHD = rs.getInt(1);
                    }

                    // INSERT CHI TIẾT
                    String sqlCT = "INSERT INTO HoaDonChiTiet(maHoaDon, maDoUong, donGia, soLuong) VALUES (?,?,?,?)";

                    for (GioHang g : cart) {
                        PreparedStatement psCT = conn.prepareStatement(sqlCT);

                        psCT.setInt(1, maHD);
                        psCT.setInt(2, g.getMaDoUong());
                        psCT.setInt(3, g.getDonGia()); // ⚠️ hoặc getGiaTien()
                        psCT.setInt(4, g.getSoLuong());

                        psCT.executeUpdate();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                // 🔥 SHOW POPUP
                request.setAttribute("hoaDon", hoaDonTam);
                request.setAttribute("openModal", true);

                // 🔥 CLEAR CART SAU KHI COPY
                cart.clear();
            }
        }

        // lưu lại session
        session.setAttribute("cart", cart);

        // load lại dữ liệu
        request.setAttribute("listDoUong", doUongDAO.findAll());
        request.setAttribute("loaiList", loaiDAO.selectAll());

        request.getRequestDispatcher("taohoadon.jsp").forward(request, response);
    }
}