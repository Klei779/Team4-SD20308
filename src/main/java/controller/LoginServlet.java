package controller;

import dao.NguoiDungDAOImpl;
import entity.NguoiDung;
import util.AuthUtil;
import util.ParamUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private NguoiDungDAOImpl dao = new NguoiDungDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 👉 nếu đã login thì quay về home


        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = ParamUtil.getString(request, "username", "");
        String password = ParamUtil.getString(request, "password", "");

        if (username.isEmpty() || password.isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập đầy đủ thông tin!");
            request.getRequestDispatcher("/home.jsp").forward(request, response);
            return;
        }

        NguoiDung user = dao.login(username, password);

        if (user != null) {

            // ✅ lưu session
            AuthUtil.setUser(request, user);

            // 👉 ADMIN vào manager
            if ("ADMIN".equalsIgnoreCase(user.getVaiTro())) {
                response.sendRedirect(request.getContextPath() + "/quanly");
            } else {
                // 👉 user thường về home
                response.sendRedirect(request.getContextPath() + "/nhanvien/trangchu");
            }

        } else {
            request.setAttribute("error", "Sai tài khoản hoặc mật khẩu!");
            request.getRequestDispatcher("/home.jsp").forward(request, response);
        }
    }

}