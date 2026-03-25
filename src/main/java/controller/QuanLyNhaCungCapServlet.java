package controller;

import dao.NhaCungCapDAO;
import dao.NhaCungCapDAOImpl;
import entity.NhaCungCap;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/quanly/quanlynhacungcap")
public class QuanLyNhaCungCapServlet extends HttpServlet {
    private NhaCungCapDAO dao = new NhaCungCapDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String txtSearch = request.getParameter("txtSearch");
        List<NhaCungCap> list;

        if (txtSearch != null && !txtSearch.trim().isEmpty()) {
            list = dao.findByName(txtSearch);
        } else {
            list = dao.findAll();
        }

        request.setAttribute("dsNCC", list);
        request.getRequestDispatcher("/QuanLyNhaCungCap.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        try {
            if ("save".equals(action)) {
                String idStr = request.getParameter("maNhaCungCap");
                NhaCungCap ncc = new NhaCungCap();
                ncc.setTenNhaCungCap(request.getParameter("tenNhaCungCap"));
                ncc.setDienThoai(request.getParameter("dienThoai"));
                ncc.setDiaChi(request.getParameter("diaChi"));

                if (idStr == null || idStr.isEmpty()) {
                    dao.insert(ncc); // Thêm mới
                } else {
                    ncc.setMaNhaCungCap(Integer.parseInt(idStr));
                    dao.update(ncc); // Cập nhật
                }
            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("maNhaCungCap"));
                NhaCungCap ncc = new NhaCungCap();
                ncc.setMaNhaCungCap(id);
                dao.delete(ncc); // Xóa
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("/quanly/quanlynhacungcap");
    }
}