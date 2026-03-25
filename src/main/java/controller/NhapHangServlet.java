package controller;

import dao.PhieuNhapKhoDAOImpl;
import dao.PhieuNhapKhoChiTietDAOImpl;
import entity.PhieuNhapKho;
import entity.PhieuNhapKhoChiTiet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.*;

@WebServlet("/nhaphang")
public class NhapHangServlet extends HttpServlet {

    private PhieuNhapKhoDAOImpl dao = new PhieuNhapKhoDAOImpl();
    private PhieuNhapKhoChiTietDAOImpl ctDAO = new PhieuNhapKhoChiTietDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Map<String, Object>> viewList = new ArrayList<>();

        try {

            // ================== CLICK XEM CHI TIẾT ==================
            String action = request.getParameter("action");
            String idStr = request.getParameter("id");

            if ("detail".equals(action) && idStr != null && !idStr.equals("null")) {

                int id = Integer.parseInt(idStr);

                List<PhieuNhapKhoChiTiet> listCT = ctDAO.findByPhieuNhapKho(id);

                List<Map<String, Object>> ctView = new ArrayList<>();

                for (PhieuNhapKhoChiTiet ct : listCT) {

                    Map<String, Object> map = new HashMap<>();

                    map.put("tenNL", ctDAO.getTenNguyenLieu(ct.getMaNguyenLieu()));
                    map.put("soLuong", ct.getSoLuong());
                    map.put("donGia", ct.getDonGiaNhap());
                    map.put("hsd", ct.getNgayHetHan());
                    map.put("thanhTien", ct.getSoLuong() * ct.getDonGiaNhap());

                    ctView.add(map);
                }

                request.setAttribute("ctList", ctView);
                request.setAttribute("openModal", true);
                request.setAttribute("maPN", id);
            }

            // ================== DANH SÁCH ==================
            List<PhieuNhapKho> list = dao.findAll();

            for (PhieuNhapKho p : list) {

                Map<String, Object> map = new HashMap<>();

                map.put("maPN", p.getMaPhieuNhapKho());
                map.put("ngay", p.getNgayNhapKho());
                map.put("tongTien", p.getTongTien());
                map.put("nhanVien", dao.getTenNhanVien(p.getMaNguoiDung()));
                map.put("ncc", dao.getTenNCC(p.getMaNCC()));

                viewList.add(map);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("list", viewList);
        request.getRequestDispatcher("nhaphang.jsp").forward(request, response);
    }
}