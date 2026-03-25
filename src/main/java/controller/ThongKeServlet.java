package controller;

import dao.ThongKeDAO;
import dao.ThongKeDAOImpl;
import entity.ThongKeDTO;
import entity.ThongKeDoUongDTO;
import entity.ThongKeNhanVienDTO;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.*;

@WebServlet("/quanly/thongke")
public class ThongKeServlet extends HttpServlet {

    private ThongKeDAO thongKeDAO = new ThongKeDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String range = request.getParameter("range");

            Date toDate = new Date();
            Date fromDate;

            Calendar cal = Calendar.getInstance();
            cal.setTime(toDate);

            if ("today".equals(range)) {
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
                fromDate = cal.getTime();

            } else if ("30days".equals(range)) {
                cal.add(Calendar.DAY_OF_MONTH, -30);
                fromDate = cal.getTime();

            } else {
                cal.add(Calendar.DAY_OF_MONTH, -7);
                fromDate = cal.getTime();
                range = "7days"; // để giữ selected
            }

            ThongKeDTO tk = thongKeDAO.getThongKe(fromDate, toDate);
            List<ThongKeDoUongDTO> topDoUong = thongKeDAO.getTopDoUong(fromDate, toDate);
            List<ThongKeNhanVienDTO> nhanVien = thongKeDAO.getDoanhThuNhanVien(fromDate, toDate);

            request.setAttribute("tk", tk);
            request.setAttribute("topDoUong", topDoUong);
            request.setAttribute("nhanVien", nhanVien);
            request.setAttribute("range", range); // ⭐ thêm dòng này

            request.getRequestDispatcher("/thongke.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi thống kê!");
        }
    }
}