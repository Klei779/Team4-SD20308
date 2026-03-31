package controller;

import dao.*;
import entity.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/congthuc")
public class CongThucServlet extends HttpServlet {

    CongThucDAO ctDAO = new CongThucDAOImpl();
    CongThucCTDAO ctctDAO = new CongThucCTDAOImpl();
    NguyenLieuDAO nlDAO = new NguyenLieuDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "list":
                req.setAttribute("list", ctDAO.findall());
                req.getRequestDispatcher("congthuc.jsp").forward(req, resp);
                break;

            case "getCTCT":
                resp.setContentType("application/json; charset=UTF-8");
                int maCT = Integer.parseInt(req.getParameter("id"));
                List<CongThucChiTiet> list = ctctDAO.findByCongThuc(maCT);
                StringBuilder json = new StringBuilder("[");
                for (int i = 0; i < list.size(); i++) {
                    CongThucChiTiet c = list.get(i);
                    NguyenLieu nl = nlDAO.findById(c.getMaNguyenLieu());
                    String tenNL = (nl != null) ? nl.getTenNguyenLieu() : "N/A";
                    json.append("{\"maCTCT\":").append(c.getMaCTCT())
                            .append(",\"tenNL\":\"").append(tenNL).append("\"")
                            .append(",\"dinhLuong\":").append(c.getDinhLuong()).append("}");
                    if (i < list.size() - 1) json.append(",");
                }
                json.append("]");
                resp.getWriter().write(json.toString());
                break;

            case "getNguyenLieu":
                resp.setContentType("application/json; charset=UTF-8");
                List<NguyenLieu> nlList = nlDAO.findAll();
                StringBuilder jsonNL = new StringBuilder("[");
                for (int i = 0; i < nlList.size(); i++) {
                    NguyenLieu nl = nlList.get(i);
                    jsonNL.append("{\"id\":").append(nl.getMaNguyenLieu())
                            .append(",\"ten\":\"").append(nl.getTenNguyenLieu()).append("\"}");
                    if (i < nlList.size() - 1) jsonNL.append(",");
                }
                jsonNL.append("]");
                resp.getWriter().write(jsonNL.toString());
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("insert".equals(action)) {
            String ten = req.getParameter("ten");
            CongThuc ct = new CongThuc(0, ten, true);
            ctDAO.insert(ct);
        }
        else if ("softDelete".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));

            // 1. Tìm công thức bằng ID (Dùng hàm findByCongThuc sẵn có của bạn)
            List<CongThuc> list = ctDAO.findByCongThuc(id);

            if (list != null && !list.isEmpty()) {
                CongThuc ct = list.get(0);

                // 2. Đảo ngược trạng thái (Nếu đang hiện thì thành ẩn, đang ẩn thành hiện)
                // Dùng toán tử ! để đảo giá trị boolean
                ct.setTrangThai(!ct.isTrangThai());

                // 3. Gọi hàm update để lưu trạng thái mới vào Database
                ctDAO.update(ct);
            }
        }
        else if ("resetData".equals(action)) { // Xóa vĩnh viễn
            int id = Integer.parseInt(req.getParameter("id"));
            ctDAO.resetData(id);
        }
        else if ("addCTCT_AJAX".equals(action)) {
            resp.setContentType("application/json");
            int maCT = Integer.parseInt(req.getParameter("maCongThuc"));
            int maNL = Integer.parseInt(req.getParameter("maNguyenLieu"));
            int dl = Integer.parseInt(req.getParameter("dinhLuong"));
            ctctDAO.insert(new CongThucChiTiet(0, maCT, maNL, dl));
            resp.getWriter().write("{\"status\":\"success\"}");
            return;
        }
        else if ("deleteCTCT_AJAX".equals(action)) {
            resp.setContentType("application/json");
            int id = Integer.parseInt(req.getParameter("id"));
            ctctDAO.delete(id);
            resp.getWriter().write("{\"status\":\"success\"}");
            return;
        }

        resp.sendRedirect("congthuc");
    }
}