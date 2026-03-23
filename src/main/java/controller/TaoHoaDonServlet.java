package controller;

import dao.*;
import entity.*;
import util.AuthUtil;
import util.JDBC;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/nhanvien/taohoadon")
public class TaoHoaDonServlet extends HttpServlet {

    DoUongDAOImpl doUongDAO = new DoUongDAOImpl();
    LoaiDoUongDAOImpl loaiDAO = new LoaiDoUongDAOImpl();
    CongThucCTDAO ctctDAO = new CongThucCTDAOImpl();
    NguyenLieuDAO nguyenLieuDAO = new NguyenLieuDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("keyword");
        String maLoai = request.getParameter("maLoai");

        List<DoUong> list;

        if (keyword != null && !keyword.trim().isEmpty()) {
            list = doUongDAO.findByTenDoUong(keyword);
        } else if (maLoai != null && !maLoai.isEmpty()) {
            list = doUongDAO.findByMaLoai(Integer.parseInt(maLoai));
        } else {
            list = doUongDAO.findAll();
        }

        request.setAttribute("listDoUong", list);
        request.setAttribute("loaiList", loaiDAO.selectAll());
        request.getRequestDispatcher("/taohoadon.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        List<GioHang> cart = (List<GioHang>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();

        String action = request.getParameter("action");

        if ("them".equals(action)) {
            int maDoUong = Integer.parseInt(request.getParameter("maDoUong"));
            DoUong drink = doUongDAO.findById(maDoUong);
            boolean exists = false;
            for (GioHang g : cart) {
                if (g.getMaDoUong() == maDoUong) {
                    g.setSoLuong(g.getSoLuong() + 1);
                    exists = true;
                    break;
                }
            }
            if (!exists) cart.add(new GioHang(drink.getMaDoUong(), drink.getTenDoUong(), drink.getGiaTien(), 1));

        } else if ("xoaAll".equals(action)) {
            cart.clear();

        } else if ("thanhtoan".equals(action)) {

            response.setContentType("text/html;charset=UTF-8");

            if (cart.isEmpty()) {
                response.getWriter().println("<p>Giỏ hàng trống!</p>");
                return;
            }

            String ghiChu = request.getParameter("ghiChu"); // Lấy ghi chú

            // Kiểm tra nguyên liệu
            for (GioHang g : cart) {
                DoUong d = doUongDAO.findById(g.getMaDoUong());
                List<CongThucChiTiet> listCT = ctctDAO.findByCongThuc(d.getMaCongThuc());

                for (CongThucChiTiet c : listCT) {
                    NguyenLieu nl = nguyenLieuDAO.findById(c.getMaNguyenLieu());
                    int can = g.getSoLuong() * c.getDinhLuong();
                    if (nl.getSoLuongTon() < can) {
                        response.getWriter().println("<p>Không đủ nguyên liệu!</p>");
                        return;
                    }
                }
            }

            Connection conn = null;

            try {
                conn = JDBC.getConnection();
                conn.setAutoCommit(false);

                int tongThanhTien = cart.stream().mapToInt(GioHang::getThanhTien).sum();

                HoaDonDAO hoaDonDAO = new HoaDonDAOImpl();
                HoaDonChiTietDAO hdctDAO = new HoaDonChiTietDAOImpl();

                NguoiDung user = AuthUtil.getUser(request);
                if (user == null) {
                    response.getWriter().println("<p>Chưa đăng nhập!</p>");
                    return;
                }

                HoaDon hd = new HoaDon();
                hd.setMaNguoiDung(user.getMaNguoiDung());
                hd.setTrangThai(true);
                hd.setTongTien(tongThanhTien);
                hd.setNgayTao(new Timestamp(System.currentTimeMillis()));

                int maHD = hoaDonDAO.insertReturnId(hd, conn);

                double vat = tongThanhTien * 0.08;
                double total = tongThanhTien + vat;

                StringBuilder html = new StringBuilder();

                html.append("<div style='background:#fff;color:#000;padding:20px;border-radius:10px;font-family:Arial,sans-serif;max-width:400px;margin:0 auto'>");

                html.append("<h4 style='text-align:center;margin-bottom:5px;'>PolyCoffe</h4>");
                html.append("<p style='text-align:center;font-size:12px;margin-top:0'>Hệ thống quản lý nhà hàng</p>");
                html.append("<hr>");

                html.append("<div style='display:flex;justify-content:space-between;'><span>Mã HĐ:</span><span>HD").append(maHD).append("</span></div>");
                html.append("<div style='display:flex;justify-content:space-between;'><span>Nhân viên:</span><span>")
                        .append(user != null ? user.getTenNguoiDung() : "")
                        .append("</span></div>");
                html.append("<div style='display:flex;justify-content:space-between;'><span>Thời gian:</span><span>")
                        .append(new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date()))
                        .append("</span></div>");
                html.append("<hr>");

                // Chi tiết đồ uống
                for (GioHang g : cart) {
                    HoaDonChiTiet ct = new HoaDonChiTiet();
                    ct.setMaHoaDon(maHD);
                    ct.setMaDoUong(g.getMaDoUong());
                    ct.setDonGia(g.getDonGia());
                    ct.setSoLuong(g.getSoLuong());
                    hdctDAO.insert(ct, conn);

                    html.append("<div style='display:flex;justify-content:space-between;'>")
                            .append("<span>").append(g.getTenDoUong()).append(" x").append(g.getSoLuong()).append("</span>")
                            .append("<span>").append(g.getThanhTien()).append(" đ</span>")
                            .append("</div>");
                }

                conn.commit();

                // Update nguyên liệu
                for (GioHang g : cart) {
                    DoUong d = doUongDAO.findById(g.getMaDoUong());
                    List<CongThucChiTiet> listCT = ctctDAO.findByCongThuc(d.getMaCongThuc());

                    for (CongThucChiTiet c : listCT) {
                        int soLuongTru = g.getSoLuong() * c.getDinhLuong();
                        nguyenLieuDAO.updateSoLuong(c.getMaNguyenLieu(), -soLuongTru);
                    }
                }

                html.append("<hr>");
                html.append("<div style='display:flex;justify-content:space-between;'><span>Tạm tính:</span><span>").append(tongThanhTien).append(" đ</span></div>");
                html.append("<div style='display:flex;justify-content:space-between;'><span>VAT (8%):</span><span>").append((int) vat).append(" đ</span></div>");
                html.append("<div style='display:flex;justify-content:space-between;font-weight:bold;'><span>Tổng:</span><span>").append((int) total).append(" đ</span></div>");

                html.append("<hr>");

                // Ghi chú trước "Đã thanh toán"
                if (ghiChu != null && !ghiChu.trim().isEmpty()) {
                    html.append("<p><strong>Ghi chú:</strong> ").append(ghiChu).append("</p>");
                }

                html.append("<p style='text-align:center;font-weight:bold;color:green;'>Đã thanh toán</p>");
                html.append("<p style='text-align:center;margin-top:10px;'>Cảm ơn quý khách!</p>");

                html.append("</div>");

                cart.clear();
                session.setAttribute("cart", cart);

                response.getWriter().println(html.toString());
                return;

            } catch (Exception e) {
                e.printStackTrace();
                if (conn != null) {
                    try {
                        conn.rollback();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                response.getWriter().println("<p>Lỗi thanh toán!</p>");
                return;

            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // Cập nhật giỏ hàng + list đồ uống khi không phải thanh toán
        session.setAttribute("cart", cart);
        request.setAttribute("listDoUong", doUongDAO.findAll());
        request.setAttribute("loaiList", loaiDAO.selectAll());
        request.getRequestDispatcher("/taohoadon.jsp").forward(request, response);
    }
}