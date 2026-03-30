package controller;

import dao.NguoiDungDAO;
import dao.NguoiDungDAOImpl;
import entity.NguoiDung;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.MailUtil;
import util.PasswordUtil;

import java.io.IOException;

@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {

    private NguoiDungDAO dao = new NguoiDungDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");

        // 1. Check email tồn tại
        NguoiDung user = dao.findByEmail(email);

        if (user == null) {
            request.setAttribute("error", "Email không tồn tại!");
        } else {
            // 2. Tạo password mới
            String newPass = PasswordUtil.generateRandomPassword(8);

            // 3. Update DB
            boolean updated = dao.updatePassword(email, newPass);

            if (updated) {
                // 4. Gửi mail
                MailUtil.sendEmail(email, newPass);

                request.setAttribute("message", "Mật khẩu mới đã được gửi qua email!");
            } else {
                request.setAttribute("error", "Không thể cập nhật mật khẩu!");
            }
        }

        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
}