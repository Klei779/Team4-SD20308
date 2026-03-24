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
@MultipartConfig // BẮT BUỘC phải có để xử lý file
public class CapNhatHoSoServlet extends HttpServlet {
    private NguoiDungDAOImpl dao = new NguoiDungDAOImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Thiết lập Encoding để không bị lỗi font Tiếng Việt
        request.setCharacterEncoding("UTF-8");

        // 1. Lấy User từ Session
        NguoiDung user = AuthUtil.getUser(request);
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 2. Lấy dữ liệu chữ (Dùng request.getParameter sau khi có @MultipartConfig vẫn được ở một số server,
        // nhưng an toàn nhất là lấy qua Part nếu getParameter trả về null)
        String hoTen = request.getParameter("tenNguoiDung");
        String email = request.getParameter("email");

        // 3. Xử lý File ảnh
        Part filePart = request.getPart("hinhAnh");
        String fileName = filePart.getSubmittedFileName();

        if (fileName != null && !fileName.isEmpty()) {
            // Định nghĩa thư mục lưu trữ trong máy (Path thực tế của Webapp)
            String uploadPath = getServletContext().getRealPath("/uploads");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();

            // Lưu file vào thư mục uploads
            filePart.write(uploadPath + File.separator + fileName);

            // Cập nhật tên file vào đối tượng để lưu xuống DB
            user.setHinhAnh(fileName);
        }

        // 4. Cập nhật các thông tin khác
        if (hoTen != null) user.setTenNguoiDung(hoTen);
        if (email != null) user.setEmail(email);

        // 5. Lưu vào Database & Cập nhật Session
        try {
            dao.update(user);
            AuthUtil.setUser(request, user);
            response.sendRedirect(request.getContextPath() + "/quanly?status=success");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi: " + e.getMessage());
        }
    }
}