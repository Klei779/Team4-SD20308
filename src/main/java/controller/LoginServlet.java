package controller;

import entity.NguoiDung;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.AuthUtil;
import util.ParamUtil;
import util.JDBC;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userParam = ParamUtil.getString(request, "username", "");
        String passParam = ParamUtil.getString(request, "password", "");

        try (Connection conn = JDBC.getConnection()) {
            String sql = "SELECT * FROM NguoiDung WHERE tenDangNhap=? AND matKhau=? AND trangThai=1";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userParam);
            ps.setString(2, passParam);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                NguoiDung nd = new NguoiDung();
                nd.setMaNguoiDung(rs.getInt("maNguoiDung"));
                nd.setTenNguoiDung(rs.getString("tenNguoiDung"));
                nd.setTenDangNhap(rs.getString("tenDangNhap"));
                nd.setEmail(rs.getString("email"));
                nd.setVaiTro(rs.getString("vaiTro"));
                nd.setTrangThai(rs.getBoolean("trangThai"));
                nd.setHinhAnh(rs.getString("hinhAnh"));

                AuthUtil.setUser(request, nd);

                if (AuthUtil.isManager(request)) {
                    response.sendRedirect(request.getContextPath() + "/manager/dashboard");
                } else {
                    response.sendRedirect(request.getContextPath() + "/nhanvien/trangchu");
                }

            } else {
                request.setAttribute("error", "Tài khoản, mật khẩu không đúng hoặc đã bị khóa!");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi kết nối cơ sở dữ liệu!");
        }
    }
}