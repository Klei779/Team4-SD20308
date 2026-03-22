package controller;

import dao.*;
import entity.*;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/khonguyenlieu")
public class KhoNguyenLieuServlet extends HttpServlet {

    NguyenLieuDAOImpl nguyenLieuDAO = new NguyenLieuDAOImpl();
    CongThucCTDAOImpl congThucCTDAO = new CongThucCTDAOImpl();
    LoaiNguyenLieuDAOImpl loaiDAO = new LoaiNguyenLieuDAOImpl();

    private int parseIntSafe(String value) {
        if (value == null || value.trim().isEmpty()) return 0;
        return Integer.parseInt(value);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<NguyenLieu> list = nguyenLieuDAO.findAll();
        List<LoaiNguyenLieu> listLoai = loaiDAO.findall();

        List<NguyenLieu> sapHet = new ArrayList<>();

        for (NguyenLieu nl : list) {
            if (nl.getSoLuongTon() <= nl.getSoLuongToiThieu()) {
                sapHet.add(nl);
            }
        }

        request.setAttribute("list", list);
        request.setAttribute("sapHet", sapHet);
        request.setAttribute("listLoai", listLoai);

        request.getRequestDispatcher("khonguyenlieu.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        System.out.println("ACTION = " + action);

        if ("add".equals(action)) {

            NguyenLieu nl = new NguyenLieu();

            nl.setTenNguyenLieu(request.getParameter("ten"));
            nl.setSoLuongTon(parseIntSafe(request.getParameter("soLuong")));
            nl.setDonVi(request.getParameter("donVi"));
            nl.setSoLuongToiThieu(parseIntSafe(request.getParameter("toiThieu")));
            nl.setMaLoaiNguyenLieu(parseIntSafe(request.getParameter("maLoai")));
            String ghiChu = request.getParameter("ghiChu");
            nl.setGhiChu(ghiChu == null ? "" : ghiChu);

            try {
                nguyenLieuDAO.insert(nl);
                request.getSession().setAttribute("message", "Thêm thành công!");
            } catch (Exception e) {
                e.printStackTrace();
                request.getSession().setAttribute("error", "Thêm thất bại!");
            }
        }

        else if ("update".equals(action)) {

            NguyenLieu nl = new NguyenLieu();

            nl.setMaNguyenLieu(parseIntSafe(request.getParameter("id")));

            nl.setTenNguyenLieu(request.getParameter("ten"));
            nl.setSoLuongTon(parseIntSafe(request.getParameter("soLuong")));
            nl.setDonVi(request.getParameter("donVi"));
            nl.setSoLuongToiThieu(parseIntSafe(request.getParameter("toiThieu")));
            nl.setMaLoaiNguyenLieu(parseIntSafe(request.getParameter("maLoai")));
            nl.setGhiChu(request.getParameter("ghiChu"));

            nguyenLieuDAO.update(nl);

            request.getSession().setAttribute("message", "Cập nhật thành công!");
        }

        else if ("delete".equals(action)) {

            int id = parseIntSafe(request.getParameter("id"));

            boolean dangDuocDung = congThucCTDAO.existsByNguyenLieu(id);

            if (dangDuocDung) {
                request.getSession().setAttribute("error",
                        "Không thể xóa! Nguyên liệu đang được sử dụng.");
            } else {
                nguyenLieuDAO.delete(id);
                request.getSession().setAttribute("message", "Xóa thành công!");
            }
        }

        else if ("nhap".equals(action)) {

            int id = parseIntSafe(request.getParameter("id"));
            int soLuongNhap = parseIntSafe(request.getParameter("soLuongNhap"));

            nguyenLieuDAO.updateSoLuong(id, soLuongNhap);

            request.getSession().setAttribute("message", "Nhập kho thành công!");
        }

        response.sendRedirect("khonguyenlieu");
    }
}