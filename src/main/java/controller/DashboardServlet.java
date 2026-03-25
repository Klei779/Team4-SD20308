package controller;

import dao.ThongKeDAO;
import dao.ThongKeDAOImpl;
import entity.ThongKeDTO;
import entity.ThongKeDoUongDTO;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.*;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private ThongKeDAO thongKeDAO = new ThongKeDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // ===== LẤY NGÀY HÔM NAY =====
            Calendar cal = Calendar.getInstance();

            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            Date fromDate = cal.getTime();

            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            Date toDate = cal.getTime();

            // ===== THỐNG KÊ =====
            ThongKeDTO tk = thongKeDAO.getThongKe(fromDate, toDate);

            request.setAttribute("revenueToday", tk.getDoanhThu());
            request.setAttribute("ordersToday", tk.getSoHoaDon());
            request.setAttribute("pendingBills", 0); // chưa làm thì để tạm
            request.setAttribute("activeStaff", 0);

            // ===== TOP ĐỒ UỐNG =====
            List<ThongKeDoUongDTO> topList =
                    thongKeDAO.getTopDoUong(fromDate, toDate);

            request.setAttribute("topList", topList);

            // ===== HÓA ĐƠN GẦN ĐÂY (FAKE TẠM - bạn có thể thay DB sau) =====
            List<Map<String, Object>> billList = new ArrayList<>();

            for (int i = 0; i < 5; i++) {
                Map<String, Object> b = new HashMap<>();
                b.put("maHoaDon", "HD" + (1340 - i));
                b.put("tongTien", (i + 1) * 50000);
                billList.add(b);
            }

            request.setAttribute("billList", billList);

            // ===== NGUYÊN LIỆU SẮP HẾT (FAKE) =====
            request.setAttribute("lowStockName", "Dầu chiên");
            request.setAttribute("lowStockAmount", 7.95);
            request.setAttribute("lowStockThreshold", 10);

            // ===== FORWARD CUỐI CÙNG =====
            request.getRequestDispatcher("dashboard.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}