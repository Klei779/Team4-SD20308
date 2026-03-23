package controller;

import dao.DoUongDAO;
import dao.DoUongDAOImpl;
import entity.DoUong;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/nhanvien/quanlydouong")
public class QuanLyDoUongServlet extends HttpServlet {
    private DoUongDAO dao = new DoUongDAOImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String txtSearch = request.getParameter("txtSearch");
        String maLoaiStr = request.getParameter("maLoai");
        List<DoUong> list;

        // Xử lý logic tìm kiếm và lọc
        if (txtSearch != null && !txtSearch.trim().isEmpty()) {
            list = dao.findByTenDoUong(txtSearch);
        } else if (maLoaiStr != null && !maLoaiStr.equals("all")) {
            list = dao.findByMaLoai(Integer.parseInt(maLoaiStr));
        } else {
            list = dao.findAll();
        }

        request.setAttribute("dsDoUong", list);
        request.getRequestDispatcher("/QuanLyDoUong.jsp").forward(request, response);
    }
}