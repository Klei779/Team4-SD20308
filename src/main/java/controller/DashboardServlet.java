package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dao.ThongKeDAO;
import dao.ThongKeDAOImpl;
import entity.ThongKeDTO;
import entity.ThongKeDoUongDTO;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ThongKeDAO dao = new ThongKeDAOImpl();

        try {
            // 👉 lấy ngày hôm nay
            Date now = new Date();
            Date start = new Date(now.getYear(), now.getMonth(), now.getDate(), 0, 0, 0);
            Date end = new Date();

            // 👉 thống kê tổng
            ThongKeDTO tk = dao.getThongKe(start, end);

            request.setAttribute("revenueToday", tk.getDoanhThu());
            request.setAttribute("ordersToday", tk.getSoHoaDon());
            request.setAttribute("pendingBills", 0); // bạn tự xử lý thêm nếu có
            request.setAttribute("activeStaff", 0);  // nếu có thì query thêm

            // 👉 top đồ uống
            List<ThongKeDoUongDTO> topList = dao.getTopDoUong(start, end);

            List<String> names = new ArrayList<>();
            List<Integer> counts = new ArrayList<>();

            for (ThongKeDoUongDTO d : topList) {
                names.add(d.getTenDoUong());
                counts.add(d.getSoLuong());
            }

            request.setAttribute("topFoods", names.toArray(new String[0]));
            request.setAttribute("topCounts", counts.stream().mapToInt(i -> i).toArray());

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 👉 forward PHẢI nằm cuối cùng
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
}