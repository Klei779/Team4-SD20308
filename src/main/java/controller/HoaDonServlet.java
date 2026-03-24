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
            String action = request.getParameter("action");

            // ===== CHỈ xử lý modal =====
            if ("detail".equals(action)) {
                int maHoaDon = Integer.parseInt(request.getParameter("id"));

                List<HoaDonChiTiet> listCT = hdctDAO.selectByHoaDonId(maHoaDon);
                List<Map<String, Object>> ctView = new ArrayList<>();

                int tong = 0;

                for (HoaDonChiTiet ct : listCT) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("maHoaDon", ct.getMaHoaDon());
                    map.put("maDoUong", ct.getMaDoUong());
                    map.put("soLuong", ct.getSoLuong());
                    map.put("donGia", ct.getDonGia());

                    int thanhTien = ct.getSoLuong() * ct.getDonGia();
                    map.put("thanhTien", thanhTien);

                    tong += thanhTien;
                    ctView.add(map);
                }

                HoaDon hd = hoaDonDAO.selectById(maHoaDon);

                String time = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                        .format(hd.getNgayTao());

                request.setAttribute("ctList", ctView);
                request.setAttribute("maHD", maHoaDon);
                request.setAttribute("nhanVien", hoaDonDAO.getTenNhanVien(hd.getMaNguoiDung()));
                request.setAttribute("thoiGian", time);
                request.setAttribute("tongTien", tong);
                request.setAttribute("openModal", true);
            }

            // mặc định lấy hết
            list = hoaDonDAO.selectAll();

            // ===== FILTER TRƯỚC =====
            if (filter != null && !filter.equals("all")) {

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

            // ===== KEYWORD SAU =====
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
            }

            // ===== map ra view =====
            for (HoaDon item : list) {
                Map<String, Object> map = new HashMap<>();

                map.put("maHoaDon", item.getMaHoaDon());

                try {
                    map.put("nhanVien", hoaDonDAO.getTenNhanVien(item.getMaNguoiDung()));
                } catch (Exception e) {
                    map.put("nhanVien", "N/A");
                }

                map.put("trangThai", item.isTrangThai());
                map.put("tongTien", item.getTongTien());
                map.put("ngayTao", item.getNgayTao());

                try {
                    int soMon = hdctDAO.selectByHoaDonId(item.getMaHoaDon()).size();
                    map.put("soMon", soMon);
                } catch (Exception e) {
                    map.put("soMon", 0);
                }

                viewList.add(map);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // ===== forward =====
        request.setAttribute("list", viewList);
        request.setAttribute("keyword", keyword);
        request.setAttribute("filter", filter);

        request.getRequestDispatcher("/hoadon.jsp").forward(request, response);
    }
}