package controller;

import dao.NguoiDungDAO;
import dao.NguoiDungDAOImpl;
import entity.NguoiDung;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import util.AuthUtil;

import java.io.File;
import java.io.IOException;

@WebServlet("/CapNhatHoSoServlet")
@MultipartConfig
public class CapNhatHoSoServlet extends HttpServlet {
    private NguoiDungDAOImpl dao = new NguoiDungDAOImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 1. Kiểm tra Session
        NguoiDung user = AuthUtil.getUser(request);
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 2. Lấy dữ liệu từ Form
        String hoTen = request.getParameter("tenNguoiDung");
        String email = request.getParameter("email");

        // 3. Xử lý File ảnh
        Part filePart = request.getPart("hinhAnh");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = filePart.getSubmittedFileName();
            String uploadPath = getServletContext().getRealPath("/uploads");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();

            filePart.write(uploadPath + File.separator + fileName);
            user.setHinhAnh(fileName);
        }

        // 4. Cập nhật đối tượng User
        if (hoTen != null) user.setTenNguoiDung(hoTen);
        if (email != null) user.setEmail(email);

        // 5. Lưu DB và Điều hướng thông minh
        try {
            dao.update(user);
            AuthUtil.setUser(request, user);

            // --- LOGIC ĐIỀU HƯỚNG QUAY LẠI TRANG CŨ ---
            String referer = request.getHeader("Referer");
            String redirectUrl;

            if (referer != null && !referer.isEmpty()) {
                // Xóa tham số status cũ nếu có để tránh URL bị dài (ví dụ: ?status=success&status=success)
                redirectUrl = referer.split("\\?status=")[0].split("&status=")[0];

                // Kiểm tra xem URL gốc đã có dấu ? chưa
                if (redirectUrl.contains("?")) {
                    redirectUrl += "&status=success";
                } else {
                    redirectUrl += "?status=success";
                }
            } else {
                // Nếu không tìm thấy trang trước đó, mặc định về trang chủ
                redirectUrl = request.getContextPath() + "/index.jsp?status=success";
            }

            response.sendRedirect(redirectUrl);

        } catch (Exception e) {
            e.printStackTrace();
            // Nếu lỗi, quay lại trang cũ với thông báo lỗi
            String referer = request.getHeader("Referer");
            response.sendRedirect(referer != null ? referer + "?status=error" : "500.jsp");
        }
    }
}