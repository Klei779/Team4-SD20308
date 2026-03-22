package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/nhaphang")
public class NhapHangServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String[] ids = {"PN004", "PN003", "PN002", "PN001"};
        String[] dates = {"2026-03-17", "2025-01-18", "2025-01-14", "2025-01-10"};
        String[] suppliers = {
                "Công ty CoCa",
                "Công ty Nước Giải Khát Fanta",
                "Nhà phân phối XYZ",
                "Công ty Thực Phẩm ABC"
        };
        String[] items = {
                "Coca Cola thùng (2 thùng)",
                "Coca Cola thùng (20 thùng)",
                "Khoai tây (100kg)",
                "Thịt bò (50kg)"
        };
        int[] totals = {0, 6400000, 2500000, 9000000};
        String[] creators = {
                "Phạm Thị Dung",
                "Phạm Thị Dung",
                "Phạm Thị Dung",
                "Phạm Thị Dung"
        };

        request.setAttribute("ids", ids);
        request.setAttribute("dates", dates);
        request.setAttribute("suppliers", suppliers);
        request.setAttribute("items", items);
        request.setAttribute("totals", totals);
        request.setAttribute("creators", creators);

        request.getRequestDispatcher("nhaphang.jsp").forward(request, response);
    }
}