package controller;

import dao.NguoiDungDAO;
import dao.NguoiDungDAOImpl;
import entity.NguoiDung;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/DoiMatKhauServlet")
public class DoiMatKhauServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        NguoiDung user = (NguoiDung) session.getAttribute("user");

        String oldPass = request.getParameter("oldPass");
        String newPass = request.getParameter("newPass");

        if (user != null && user.getMatKhau().equals(oldPass)) {
            user.setMatKhau(newPass);

            NguoiDungDAO dao = new NguoiDungDAOImpl();
            dao.update(user); // Giả sử hàm update này cập nhật cả mật khẩu

            session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/quanly?success=2");
        } else {
            // Sai mật khẩu cũ
            response.sendRedirect(request.getContextPath() + "/quanly?error=1");
        }
    }
}