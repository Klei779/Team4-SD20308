package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/thongke")
public class ThongKeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ===== Doanh thu theo ngày =====
        String[] days = {"19/2", "20/2", "21/2", "22/2", "23/2", "24/2"};
        int[] revenues = {5300000, 4700000, 4400000, 5100000, 3800000, 4200000};

        // ===== Top món =====
        String[] foods = {"Khoai", "Kem", "Classic", "Coca", "Gà"};
        int[] quantities = {108, 96, 96, 86, 78};

        // ===== Nhân viên =====
        String[] names = {"Trần Thị Bích", "Nguyễn Văn An", "Lê Văn Cường"};
        int[] bills = {105, 98, 0};
        int[] doanhThuNV = {26571240, 22989960, 0};

        // ===== Lợi nhuận =====
        int doanhThu = 49561200;
        int chiPhi = 22302540;
        int vat = 3671200;
        int loiNhuan = 23587460;

        // ===== set dữ liệu =====
        request.setAttribute("days", days);
        request.setAttribute("revenues", revenues);

        request.setAttribute("foods", foods);
        request.setAttribute("quantities", quantities);

        request.setAttribute("names", names);
        request.setAttribute("bills", bills);
        request.setAttribute("doanhThuNV", doanhThuNV);

        request.setAttribute("doanhThu", doanhThu);
        request.setAttribute("chiPhi", chiPhi);
        request.setAttribute("vat", vat);
        request.setAttribute("loiNhuan", loiNhuan);

        request.getRequestDispatcher("thongke.jsp").forward(request, response);
    }
}