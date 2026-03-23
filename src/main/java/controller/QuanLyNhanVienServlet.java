package controller;

import dao.NguoiDungDAO;
import dao.NguoiDungDAOImpl;
import entity.NguoiDung;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import util.JDBC;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

@WebServlet("/nhanvien/quanlynhanvien")
public class QuanLyNhanVienServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String txtSearch = request.getParameter("txtSearch");
        String role = request.getParameter("role");
        String status = request.getParameter("status");

        NguoiDungDAO dao = new NguoiDungDAOImpl();
        List<NguoiDung> list;

        // Ưu tiên lọc theo Tên -> Chức vụ -> Trạng thái
        if (txtSearch != null && !txtSearch.trim().isEmpty()) {
            list = dao.findByTen(txtSearch);
        } else if (role != null && !role.trim().isEmpty()) {
            list = dao.findByVaiTro(role);
        } else if (status != null && !status.trim().isEmpty()) {
            boolean isAction = status.equals("1");
            list = dao.findByTrangThai(isAction);
        } else {
            list = dao.findAll(); // Mặc định load tất cả
        }

        request.setAttribute("dsNhanVien", list);
        request.getRequestDispatcher("/QuanLyNhanVien.jsp").forward(request, response);
    }
}