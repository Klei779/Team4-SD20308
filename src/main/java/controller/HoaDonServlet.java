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

        // ================== 1. XEM CHI TIẾT ==================
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

        // ================== 2. SEARCH + FILTER ==================
        String keyword = request.getParameter("keyword");
        String filter = request.getParameter("filter");

        List<HoaDon> list;

        try {
            // ===== SEARCH =====
            if (keyword != null && !keyword.trim().isEmpty()) {

                keyword = keyword.trim();

                // tìm theo mã người dùng
                if (keyword.matches("\\d+")) {
                    int maNguoiDung = Integer.parseInt(keyword);
                    list = hoaDonDAO.selectByMaNguoiDung(maNguoiDung);
                }
                // tìm theo trạng thái
                else if (keyword.equalsIgnoreCase("true") || keyword.equalsIgnoreCase("false")) {
                    boolean trangThai = Boolean.parseBoolean(keyword);
                    list = hoaDonDAO.selectByTrangThai(trangThai);
                }
                // không hợp lệ → rỗng
                else {
                    list = new ArrayList<>();
                }

            } else {
                // ===== FILTER =====
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

        } catch (Exception e) {
            list = new ArrayList<>();
            e.printStackTrace();
        }

        // ================== 3. BUILD VIEW ==================
        List<Map<String, Object>> viewList = new ArrayList<>();

        for (HoaDon hd : list) {

            Map<String, Object> map = new HashMap<>();

            map.put("maHoaDon", hd.getMaHoaDon());
            map.put("nhanVien", hoaDonDAO.getTenNhanVien(hd.getMaNguoiDung()));
            map.put("trangThai", hd.isTrangThai());
            map.put("tongTien", hd.getTongTien());
            map.put("ngayTao", hd.getNgayTao());
            int soMon = hdctDAO.selectByHoaDonId(hd.getMaHoaDon()).size();
            map.put("soMon", soMon);

            viewList.add(map);
        }

        // ================== 4. GỬI DATA ==================
        request.setAttribute("list", viewList);
        request.setAttribute("keyword", keyword);
        request.setAttribute("filter", filter);

        // ================== 5. FORWARD ==================
        request.getRequestDispatcher("/hoadon.jsp").forward(request, response);
    }
}