package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 🔥 DATA GIẢ (sau này bạn thay bằng DB)
        request.setAttribute("revenueToday", 648000);
        request.setAttribute("ordersToday", 12);
        request.setAttribute("pendingBills", 2);
        request.setAttribute("activeStaff", 3);

        request.setAttribute("topFoods", new String[]{
                "Classic Burger", "Nước cam ép", "Khoai tây lớn", "Coca Cola"
        });

        request.setAttribute("topCounts", new int[]{167, 158, 156, 148});

        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
        // 🔥 Nguyên liệu sắp hết
        request.setAttribute("lowStockName", "Dầu chiên");
        request.setAttribute("lowStockAmount", 7.95);
        request.setAttribute("lowStockThreshold", 10);

// 🔥 Hóa đơn gần đây
        String[] billIds = {"HD1341", "HD1340", "HD1339", "HD1338", "HD1336"};
        int[] totals = {64800, 156600, 155520, 106920, 140400};

        request.setAttribute("billIds", billIds);
        request.setAttribute("totals", totals);
    }
}