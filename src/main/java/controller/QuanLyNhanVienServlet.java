package controller;

import dao.NguoiDungDAO;
import dao.NguoiDungDAOImpl;
import entity.NguoiDung;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/quanly/quanlynhanvien")
public class QuanLyNhanVienServlet extends HttpServlet {
    private NguoiDungDAO dao = new NguoiDungDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String txtSearch = request.getParameter("txtSearch");
        String role = request.getParameter("role");
        String status = request.getParameter("status");

        List<NguoiDung> list;

        // Logic tìm kiếm lọc kết hợp đơn giản
        if (txtSearch != null && !txtSearch.trim().isEmpty()) {
            list = dao.findByTen(txtSearch);
        } else if (role != null && !role.trim().isEmpty()) {
            list = dao.findByVaiTro(role);
        } else if (status != null && !status.trim().isEmpty()) {
            list = dao.findByTrangThai(status.equals("1"));
        } else {
            list = dao.findAll();
        }

        request.setAttribute("dsNhanVien", list);
        request.getRequestDispatcher("/QuanLyNhanVien.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        try {
            if ("save".equals(action)) {
                String idStr = request.getParameter("maNguoiDung");
                NguoiDung nd = new NguoiDung();
                nd.setTenNguoiDung(request.getParameter("tenNguoiDung"));
                nd.setTenDangNhap(request.getParameter("tenDangNhap"));
                nd.setMatKhau(request.getParameter("matKhau"));
                nd.setEmail(request.getParameter("email"));
                nd.setVaiTro(request.getParameter("vaiTro"));
                nd.setTrangThai(request.getParameter("trangThai").equals("1"));
                nd.setHinhAnh("");

                if (idStr == null || idStr.isEmpty()) {
                    dao.insert(nd);
                } else {
                    nd.setMaNguoiDung(Integer.parseInt(idStr));
                    dao.update(nd);
                }
            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("maNguoiDung"));
                dao.delete(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("/quanly/quanlynhanvien");
    }
}