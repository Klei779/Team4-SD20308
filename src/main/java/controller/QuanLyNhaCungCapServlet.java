package controller;

import dao.NhaCungCapDAO;
import dao.NhaCungCapDAOImpl;
import entity.NhaCungCap;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/nhanvien/quanlynhacungcap")
public class QuanLyNhaCungCapServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String txtSearch = request.getParameter("txtSearch");
        NhaCungCapDAO dao = new NhaCungCapDAOImpl();
        List<NhaCungCap> list;

        if (txtSearch != null && !txtSearch.trim().isEmpty()) {
            list = dao.findByName(txtSearch);
        } else {
            list = dao.findAll();
        }

        request.setAttribute("dsNCC", list);
        request.getRequestDispatcher("/QuanLyNhaCungCap.jsp").forward(request, response);
    }
}