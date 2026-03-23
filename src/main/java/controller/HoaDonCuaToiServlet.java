package controller;

import entity.HoaDon;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/hoadoncuat")
public class HoaDonCuaToiServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<HoaDon> list = new ArrayList<>();

        // 🔥 DATA ẢO (dùng đúng entity của bạn)
        list.add(new HoaDon(1, 101, Timestamp.valueOf("2026-03-18 10:47:00"), true, 64800));
        list.add(new HoaDon(2, 102, Timestamp.valueOf("2026-03-17 09:55:00"), true, 156600));
        list.add(new HoaDon(3, 103, Timestamp.valueOf("2026-03-17 09:47:00"), true, 155520));
        list.add(new HoaDon(4, 104, Timestamp.valueOf("2026-03-17 08:53:00"), true, 106920));
        list.add(new HoaDon(5, 105, Timestamp.valueOf("2026-03-17 08:47:00"), false, 140400));
        list.add(new HoaDon(6, 106, Timestamp.valueOf("2026-03-17 08:47:00"), false, 140400));

        // 🔥 TÍNH TỔNG DOANH THU (chỉ tính đã thanh toán = true)
        int tong = 0;
        for (HoaDon hd : list) {
            if (hd.isTrangThai()) {
                tong += hd.getTongTien();
            }
        }

        // 🔥 GỬI SANG JSP
        request.setAttribute("listHoaDon", list);
        request.setAttribute("tongDoanhThu", tong);
        request.setAttribute("soHoaDon", list.size());

        request.getRequestDispatcher("hoadoncuatoi.jsp").forward(request, response);
    }
}