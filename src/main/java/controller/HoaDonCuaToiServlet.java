package controller;

import dao.HoaDonChiTietDAO;
import dao.HoaDonChiTietDAOImpl;
import dao.HoaDonDAO;
import dao.HoaDonDAOImpl;
import entity.HoaDon;
import entity.NguoiDung;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/hoadoncuatoi")
public class HoaDonCuaToiServlet extends HttpServlet {

    private HoaDonDAO hoaDonDAO = new HoaDonDAOImpl();
    private HoaDonChiTietDAO hdctDAO = new HoaDonChiTietDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
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
        }catch (Exception e) {
                e.printStackTrace();
            }


        // 🔥 1. LẤY USER ĐANG LOGIN
        NguoiDung user = util.AuthUtil.getUser(request);


        int maNguoiDung = user.getMaNguoiDung();

        // 🔥 2. LẤY PARAM LỌC NGÀY
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");

        List<HoaDon> list;

        // 🔥 3. XỬ LÝ LỌC
        try {
            if (fromDate != null && toDate != null &&
                    !fromDate.isEmpty() && !toDate.isEmpty()) {

                Timestamp from = Timestamp.valueOf(fromDate + " 00:00:00");
                Timestamp to = Timestamp.valueOf(toDate + " 23:59:59");

                list = hoaDonDAO.selectByMaNguoiDung(maNguoiDung)
                        .stream()
                        .filter(hd -> !hd.getNgayTao().before(from) && !hd.getNgayTao().after(to))
                        .collect(Collectors.toList());
            } else {
                list = hoaDonDAO.selectByMaNguoiDung(maNguoiDung);
            }
        } catch (Exception e) {
            e.printStackTrace();
            list = hoaDonDAO.selectByMaNguoiDung(maNguoiDung);
        }

        // 🔥 4. TÍNH DOANH THU
        int tong = 0;
        for (HoaDon hd : list) {
            if (hd.isTrangThai()) {
                tong += hd.getTongTien();
            }
        }

        // 🔥 5. GỬI SANG JSP
        request.setAttribute("listHoaDon", list);
        request.setAttribute("tongDoanhThu", tong);
        request.setAttribute("soHoaDon", list.size());
        request.getRequestDispatcher("/hoadoncuatoi.jsp").forward(request, response);
    }
    }
