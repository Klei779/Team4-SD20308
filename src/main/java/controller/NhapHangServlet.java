package controller;

import dao.PhieuNhapKhoDAO;
import dao.PhieuNhapKhoDAOImpl;
import entity.PhieuNhapKho;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/nhaphang")
public class NhapHangServlet extends HttpServlet {

    private PhieuNhapKhoDAO dao = new PhieuNhapKhoDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<PhieuNhapKho> list = dao.findAll();

        request.setAttribute("list", list);

        request.getRequestDispatcher("nhaphang.jsp").forward(request, response);
    }
}