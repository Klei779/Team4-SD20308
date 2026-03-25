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
        // Hiển thị trang login
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Lấy dữ liệu an toàn từ ParamUtil
        String username = ParamUtil.getString(request, "username", "");
        String password = ParamUtil.getString(request, "password", "");
        String errorMsg = null;

        // 2. Kiểm tra rỗng
        if (username.isEmpty() || password.isEmpty()) {
            errorMsg = "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu!";
        } else {
            try {
                // 3. Logic Check lỗi chi tiết
                // Tìm người dùng theo tên đăng nhập trước để biết tài khoản có tồn tại không
                NguoiDung user = dao.findByTenDangNhap(username);

                if (user == null) {
                    errorMsg = "Tên đăng nhập không tồn tại!";
                } else if (!user.getMatKhau().equals(password)) {
                    errorMsg = "Mật khẩu không chính xác!";
                } else if (!user.isTrangThai()) {
                    // Nếu trangThai trong DB là bit/boolean (0: Khóa, 1: Hoạt động)
                    errorMsg = "Tài khoản của bạn đã bị khóa. Vui lòng liên hệ Admin!";
                } else {
                    // ✅ ĐĂNG NHẬP THÀNH CÔNG
                    AuthUtil.setUser(request, user);

                    // 4. Phân quyền điều hướng
                    String vaiTro = user.getVaiTro();
                    if ("Quản lý".equalsIgnoreCase(vaiTro) || "Admin".equalsIgnoreCase(vaiTro)) {
                        response.sendRedirect(request.getContextPath() + "/quanly");
                    } else {
                        response.sendRedirect(request.getContextPath() + "/nhanvien");
                    }
                    return; // Kết thúc để không chạy xuống phần forward lỗi
                }
            } catch (Exception e) {
                e.printStackTrace();
                errorMsg = "Hệ thống đang gặp sự cố kết nối!";
            }
        }

        // 5. Nếu có lỗi (errorMsg != null), quay lại trang login và hiển thị thông báo
        request.setAttribute("error", errorMsg);
        request.setAttribute("oldUser", username); // Giữ lại tên để khách không phải nhập lại
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

}