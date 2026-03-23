package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // ===== LOGIN GIẢ =====
        if ("long".equals(username) && "123".equals(password)) {

            HttpSession session = request.getSession();
            session.setAttribute("user", username);

            // 👉 chuyển sang trang quản lý
            response.sendRedirect(request.getContextPath() + "/quanly");

        } else {
            // 👉 sai -> quay về home + báo lỗi
            request.setAttribute("error", "Sai tài khoản hoặc mật khẩu!");
            request.getRequestDispatcher("/home.jsp").forward(request, response);
        }
    }
}