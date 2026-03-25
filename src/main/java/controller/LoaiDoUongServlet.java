package controller;

import dao.LoaiDoUongDAO;
import dao.LoaiDoUongDAOImpl;
import dao.DoUongDAO;
import dao.DoUongDAOImpl;

import entity.LoaiDoUong;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/quanly/loaidouong")
public class LoaiDoUongServlet extends HttpServlet {

    private LoaiDoUongDAO loaiDAO = new LoaiDoUongDAOImpl();
    private DoUongDAO doUongDAO = new DoUongDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<LoaiDoUong> listLoai = loaiDAO.selectAll();
        request.setAttribute("listLoai", listLoai);

        String maLoaiStr = request.getParameter("maLoai");

        if (maLoaiStr != null && !maLoaiStr.isEmpty()) {
            try {
                int maLoai = Integer.parseInt(maLoaiStr);

                LoaiDoUong selectedLoai = loaiDAO.selectById(maLoai);
                request.setAttribute("selectedLoai", selectedLoai);

                request.setAttribute("listDoUong", doUongDAO.findByMaLoai(maLoai));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        request.getRequestDispatcher("/loaidouong.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        try {

            if ("add".equals(action)) {
                String ten = request.getParameter("tenLoai");

                if (ten != null && !ten.trim().isEmpty()) {
                    loaiDAO.insert(new LoaiDoUong(ten));
                    request.getSession().setAttribute("success", "Thêm loại thành công!");
                } else {
                    request.getSession().setAttribute("error", "Tên loại không được để trống!");
                }
            }

            else if ("update".equals(action)) {
                int id = Integer.parseInt(request.getParameter("maLoai"));
                String ten = request.getParameter("tenLoai");

                if (ten != null && !ten.trim().isEmpty()) {
                    loaiDAO.update(new LoaiDoUong(id, ten));
                    request.getSession().setAttribute("success", "Cập nhật thành công!");
                } else {
                    request.getSession().setAttribute("error", "Tên loại không hợp lệ!");
                }
            }

            else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("maLoai"));

                if (doUongDAO.findByMaLoai(id).size() > 0) {
                    request.getSession().setAttribute("error",
                            "Không thể xóa vì loại này đang có đồ uống!");
                } else {
                    loaiDAO.delete(id);
                    request.getSession().setAttribute("success", "Xóa thành công!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Có lỗi xảy ra!");
        }

        //
        response.sendRedirect("/quanly/loaidouong");
    }
}