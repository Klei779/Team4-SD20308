package controller;

import dao.HoaDonDAO;
import dao.HoaDonDAOImpl;
import dao.HoaDonChiTietDAO;
import dao.HoaDonChiTietDAOImpl;
import entity.HoaDon;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@WebServlet("/hoadon")
public class HoaDonServlet extends HttpServlet {

    private HoaDonDAO hoaDonDAO = new HoaDonDAOImpl();
    private HoaDonChiTietDAO hdctDAO = new HoaDonChiTietDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String keyword = request.getParameter("keyword");
        String filter = request.getParameter("filter");

        try {
            // 1. XỬ LÝ XEM CHI TIẾT (MODAL)
            if ("detail".equals(action)) {
                int maHoaDon = Integer.parseInt(request.getParameter("id"));

                // Lấy chi tiết hóa đơn (Phải dùng hàm JOIN với bảng DoUong để lấy hinhAnh)
                // Lưu ý: Bạn cần cập nhật hàm này trong HoaDonChiTietDAOImpl
                List<Map<String, Object>> ctView = hdctDAO.selectChiTietWithProduct(maHoaDon);

                HoaDon hd = hoaDonDAO.selectById(maHoaDon);
                if (hd != null) {
                    String time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(hd.getNgayTao());

                    // Gửi dữ liệu chi tiết sang JSP
                    request.setAttribute("ctList", ctView);
                    request.setAttribute("maHD", maHoaDon);
                    request.setAttribute("nhanVien", hoaDonDAO.getTenNhanVien(hd.getMaNguoiDung()));
                    request.setAttribute("thoiGian", time);
                    request.setAttribute("tongTienHD", hd.getTongTien());
                    request.setAttribute("openModal", true);
                }
            }

            // 2. XỬ LÝ DANH SÁCH VÀ BỘ LỌC
            List<HoaDon> listHD;

            // Ưu tiên lọc theo ngày trước
            if (filter != null && !filter.equals("all")) {
                Timestamp to = Timestamp.valueOf(LocalDateTime.now());
                Timestamp from;
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
                    default:
                        from = new Timestamp(0); // Lấy tất cả nếu lỗi
                }
                listHD = hoaDonDAO.selectByDate(from, to);
            } else {
                listHD = hoaDonDAO.selectAll();
            }

            // Lọc theo từ khóa (Mã NV hoặc Trạng thái)
            if (keyword != null && !keyword.trim().isEmpty()) {
                String k = keyword.trim().toLowerCase();
                List<HoaDon> filteredList = new ArrayList<>();

                for (HoaDon h : listHD) {
                    // Lấy tên nhân viên để so sánh chuỗi
                    String tenNV = hoaDonDAO.getTenNhanVien(h.getMaNguoiDung()).toLowerCase();

                    // 1. Kiểm tra nếu tên nhân viên chứa từ khóa (Tìm kiếm gần đúng)
                    if (tenNV.contains(k)) {
                        filteredList.add(h);
                    }
                    // 2. Kiểm tra trạng thái (giữ nguyên logic cũ)
                    else if (k.equals("true") && h.isTrangThai()) {
                        filteredList.add(h);
                    } else if (k.equals("false") && !h.isTrangThai()) {
                        filteredList.add(h);
                    } else if (k.contains("thanh toán") && h.isTrangThai()) { // Hỗ trợ gõ tiếng Việt cơ bản
                        filteredList.add(h);
                    } else if (k.contains("chờ") && !h.isTrangThai()) {
                        filteredList.add(h);
                    }
                }
                listHD = filteredList;
            }

            // 3. MAP DỮ LIỆU RA VIEW (Thêm tên nhân viên và số lượng món)
            List<Map<String, Object>> viewList = new ArrayList<>();
            for (HoaDon item : listHD) {
                Map<String, Object> map = new HashMap<>();
                map.put("maHoaDon", item.getMaHoaDon());
                map.put("nhanVien", hoaDonDAO.getTenNhanVien(item.getMaNguoiDung()));
                map.put("trangThai", item.isTrangThai());
                map.put("tongTien", item.getTongTien());
                map.put("ngayTao", item.getNgayTao());

                // Đếm số lượng món trong hóa đơn
                int soMon = hdctDAO.selectByHoaDonId(item.getMaHoaDon()).size();
                map.put("soMon", soMon);

                viewList.add(map);
            }

            // Gửi dữ liệu về trang JSP
            request.setAttribute("list", viewList);
            request.setAttribute("keyword", keyword);
            request.setAttribute("filter", filter);

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("/hoadon.jsp").forward(request, response);
    }
}