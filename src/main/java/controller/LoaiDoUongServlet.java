package controller;

import dao.LoaiDoUongDAO;
import dao.LoaiDoUongDAOImpl;
import entity.LoaiDoUong;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/loai-do-uong")
public class LoaiDoUongServlet extends HttpServlet {

    private LoaiDoUongDAO dao = new LoaiDoUongDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<LoaiDoUong> list = dao.selectAll();
        request.setAttribute("listLoai", list);

        String maLoaiStr = request.getParameter("maLoai");
        if (maLoaiStr != null) {
            int maLoai = Integer.parseInt(maLoaiStr);
            request.setAttribute("selectedLoai", dao.selectById(maLoai));
        }

        request.getRequestDispatcher("/views/loai-do-uong.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                String ten = request.getParameter("tenLoai");
                dao.insert(new LoaiDoUong(ten));
            }

            if ("update".equals(action)) {
                int id = Integer.parseInt(request.getParameter("maLoai"));
                String ten = request.getParameter("tenLoai");
                dao.update(new LoaiDoUong(id, ten));
            }

            if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("maLoai"));

                if (dao.hasDoUong(id)) {
                    request.getSession().setAttribute("error", "Không thể xóa vì loại này đang có đồ uống!");
                } else {
                    dao.delete(id);
                    request.getSession().setAttribute("success", "Xóa thành công!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("loai-do-uong");
    }
}