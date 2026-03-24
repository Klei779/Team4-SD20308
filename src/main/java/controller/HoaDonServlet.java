package controller;

import dao.HoaDonDAO;
import dao.HoaDonDAOImpl;
import dao.HoaDonChiTietDAO;
import dao.HoaDonChiTietDAOImpl;
import entity.HoaDon;
import entity.HoaDonChiTiet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@WebServlet("/hoadon")
public class HoaDonServlet extends HttpServlet {

    private HoaDonDAO hoaDonDAO = new HoaDonDAOImpl();
    private HoaDonChiTietDAO hdctDAO = new HoaDonChiTietDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Map<String, Object>> viewList = new ArrayList<>();
        List<HoaDon> list = new ArrayList<>();
        String keyword = request.getParameter("keyword");
        String filter = request.getParameter("filter");

        try {
            // ===== Chi tiết hóa đơn =====
            String action = request.getParameter("action");
            if ("detail".equals(action)) {
                try {
                    int maHoaDon = Integer.parseInt(request.getParameter("id"));
                    List<HoaDonChiTiet> listCT = hdctDAO.selectByHoaDonId(maHoaDon);
                    List<Map<String, Object>> ctView = new ArrayList<>();
                    for (HoaDonChiTiet ct : listCT) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("maHoaDon", ct.getMaHoaDon());
                        map.put("maDoUong", ct.getMaDoUong());
                        map.put("soLuong", ct.getSoLuong());
                        map.put("donGia", ct.getDonGia());
                        map.put("thanhTien", ct.getSoLuong() * ct.getDonGia());
                        ctView.add(map);
                    }
                    request.setAttribute("ctList", ctView);
                    request.setAttribute("openModal", true);
                    request.setAttribute("maHD", maHoaDon);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // ===== Tìm kiếm / lọc =====
            if (keyword != null && !keyword.trim().isEmpty()) {
                keyword = keyword.trim();
                if (keyword.matches("\\d+")) {
                    int maNguoiDung = Integer.parseInt(keyword);
                    list = hoaDonDAO.selectByMaNguoiDung(maNguoiDung);
                } else if (keyword.equalsIgnoreCase("true") || keyword.equalsIgnoreCase("false")) {
                    boolean trangThai = Boolean.parseBoolean(keyword);
                    list = hoaDonDAO.selectByTrangThai(trangThai);
                } else {
                    list = new ArrayList<>();
                }
            } else {
                if (filter == null || filter.equals("all")) {
                    list = hoaDonDAO.selectAll();
                } else {
                    Timestamp from = null;
                    Timestamp to = Timestamp.valueOf(LocalDateTime.now());
                    LocalDateTime now = LocalDateTime.now();
                    switch (filter) {
                        case "today":
                            from = Timestamp.valueOf(now.toLocalDate().atStartOfDay());
                            break;
                        case "7days":
                            from = Timestamp.valueOf(now.minusDays(7));
                            break;
                        case "month":
                            from = Timestamp.valueOf(now.minusMonths(1));
                            break;
                    }
                    list = hoaDonDAO.selectByDate(from, to);
                }
            }

            // ===== Chuyển dữ liệu ra view =====
            for (HoaDon hd : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("maHoaDon", hd.getMaHoaDon());
                try {
                    map.put("nhanVien", hoaDonDAO.getTenNhanVien(hd.getMaNguoiDung()));
                } catch (Exception e) {
                    map.put("nhanVien", "N/A");
                    e.printStackTrace();
                }
                map.put("trangThai", hd.isTrangThai());
                map.put("tongTien", hd.getTongTien());
                map.put("ngayTao", hd.getNgayTao());
                try {
                    int soMon = hdctDAO.selectByHoaDonId(hd.getMaHoaDon()).size();
                    map.put("soMon", soMon);
                } catch (Exception e) {
                    map.put("soMon", 0);
                    e.printStackTrace();
                }
                viewList.add(map);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // ===== Gửi dữ liệu ra JSP =====
        request.setAttribute("list", viewList);
        request.setAttribute("keyword", keyword);
        request.setAttribute("filter", filter);
        request.getRequestDispatcher("/hoadon.jsp").forward(request, response);
    }
}
