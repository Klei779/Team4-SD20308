package controller;

import dao.DoUongDAO;
import dao.DoUongDAOImpl;
import dao.LoaiDoUongDAO;
import dao.LoaiDoUongDAOImpl;

import entity.DoUong;
import entity.LoaiDoUong;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private DoUongDAO doUongDAO;
    private LoaiDoUongDAO loaiDAO;

    @Override
    public void init() {
        doUongDAO = new DoUongDAOImpl();
        loaiDAO = new LoaiDoUongDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ===== 1. Lấy danh sách loại (dropdown) =====
        List<LoaiDoUong> loaiList = loaiDAO.selectAll();
        request.setAttribute("loaiList", loaiList);

        // ===== 2. Lọc đồ uống =====
        String maLoaiStr = request.getParameter("maLoai");

        List<DoUong> menuList;

        if (maLoaiStr != null && !maLoaiStr.isEmpty()) {
            try {
                int maLoai = Integer.parseInt(maLoaiStr);

                // lọc theo loại + chỉ lấy đang bán
                menuList = doUongDAO.findByMaLoai(maLoai);

            } catch (NumberFormatException e) {
                // nếu lỗi thì fallback
                menuList = doUongDAO.findByTrangThai(true);
            }
        } else {
            // mặc định
            menuList = doUongDAO.findByTrangThai(true);
        }

        // ===== 3. Gửi sang JSP =====
        request.setAttribute("menuList", menuList);

        // ===== 4. Forward =====
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }
}