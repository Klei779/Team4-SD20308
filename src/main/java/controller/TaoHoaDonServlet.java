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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            DoUong d = doUongDAO.findById(maDoUong);
            boolean tonTai = false;
            for (GioHang g : cart) {
                if (g.getMaDoUong() == maDoUong) {
                    g.setSoLuong(g.getSoLuong() + 1);
                    tonTai = true;
                    break;
                }
            }
            if (!tonTai) cart.add(new GioHang(d.getMaDoUong(), d.getTenDoUong(), d.getGiaTien(), 1));

        } else if ("xoaAll".equals(action)) {
            cart.clear();

        } else if ("thanhtoan".equals(action)) {

            if (!cart.isEmpty()) {

                try {
                    // 1. Kiểm tra nguyên liệu
                    boolean duNguyenLieu = true;
                    for (GioHang g : cart) {
                        DoUong d = doUongDAO.findById(g.getMaDoUong());
                        List<CongThucChiTiet> listCT = ctctDAO.findByCongThuc(d.getMaCongThuc());
                        for (CongThucChiTiet ct : listCT) {
                            NguyenLieu nl = nguyenLieuDAO.findById(ct.getMaNguyenLieu());
                            int can = g.getSoLuong() * ct.getDinhLuong();
                            if (nl.getSoLuongTon() < can) {
                                duNguyenLieu = false;
                                break;
                            }
                        }
                        if (!duNguyenLieu) break;
                    }

                    if (!duNguyenLieu) {
                        request.setAttribute("error", "Không đủ nguyên liệu để thanh toán!");
                        request.setAttribute("listDoUong", doUongDAO.findAll());
                        request.setAttribute("loaiList", loaiDAO.selectAll());
                        request.getRequestDispatcher("/taohoadon.jsp").forward(request, response);
                        return;
                    }

                    // 2. Tạo hóa đơn
                    int tong = cart.stream().mapToInt(GioHang::getThanhTien).sum();

                    HoaDonDAO hoaDonDAO = new HoaDonDAOImpl();
                    HoaDonChiTietDAO hdctDAO = new HoaDonChiTietDAOImpl();

                    HoaDon hd = new HoaDon();
                    hd.setMaNguoiDung(1); // giả sử nhân viên
                    hd.setTrangThai(true);
                    hd.setTongTien(tong);
                    hd.setNgayTao(new Timestamp(System.currentTimeMillis()));

                    // Insert hóa đơn và lấy ID
                    int maHD = hoaDonDAO.insertReturnId(hd);

                    List<HoaDonChiTiet> chiTietList = new ArrayList<>();

                    for (GioHang g : cart) {
                        // Lưu chi tiết hóa đơn
                        HoaDonChiTiet ct = new HoaDonChiTiet();
                        ct.setMaHoaDon(maHD);
                        ct.setMaDoUong(g.getMaDoUong());
                        ct.setDonGia(g.getDonGia());
                        ct.setSoLuong(g.getSoLuong());
                        hdctDAO.insert(ct);

                        chiTietList.add(ct);

                        // Trừ nguyên liệu
                        DoUong d = doUongDAO.findById(g.getMaDoUong());
                        List<CongThucChiTiet> listCT = ctctDAO.findByCongThuc(d.getMaCongThuc());
                        for (CongThucChiTiet c : listCT) {
                            nguyenLieuDAO.updateSoLuong(c.getMaNguyenLieu(), -g.getSoLuong() * c.getDinhLuong());
                        }
                    }

                    // 3. Chuẩn bị hiển thị modal chi tiết
                    List<Map<String, Object>> ctView = new ArrayList<>();
                    for (HoaDonChiTiet ct : chiTietList) {
                        Map<String, Object> map = new HashMap<>();

                        DoUong d = doUongDAO.findById(ct.getMaDoUong()); // 👈 thêm dòng này

                        map.put("maHoaDon", ct.getMaHoaDon());
                        map.put("maDoUong", ct.getMaDoUong());
                        map.put("tenDoUong", d.getTenDoUong()); // 👈 thêm dòng này
                        map.put("donGia", ct.getDonGia());
                        map.put("soLuong", ct.getSoLuong());
                        map.put("thanhTien", ct.getDonGia() * ct.getSoLuong());

                        ctView.add(map);
                    }

                    request.setAttribute("ctList", ctView);
                    request.setAttribute("maHD", maHD);
                    request.setAttribute("openModal", true);

                    System.out.println("OPEN MODAL = TRUE");

                    cart.clear();

                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("error", "Có lỗi xảy ra khi tạo hóa đơn: " + e.getMessage());
                }
            }
        }

        session.setAttribute("cart", cart);
        request.setAttribute("listDoUong", doUongDAO.findAll());
        request.setAttribute("loaiList", loaiDAO.selectAll());
        request.getRequestDispatcher("/taohoadon.jsp").forward(request, response);
        return;
    }
}