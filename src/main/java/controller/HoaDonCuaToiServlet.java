package controller;

import dao.HoaDonDAO;
import dao.HoaDonDAOImpl;
import entity.HoaDon;
import entity.NguoiDung;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/nhanvien/hoadoncuatoi")
public class HoaDonCuaToiServlet extends HttpServlet {

    private HoaDonDAO hoaDonDAO = new HoaDonDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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