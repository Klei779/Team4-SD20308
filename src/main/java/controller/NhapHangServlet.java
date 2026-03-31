package controller;

import dao.PhieuNhapKhoDAOImpl;
import dao.PhieuNhapKhoChiTietDAOImpl;
import entity.PhieuNhapKho;
import entity.PhieuNhapKhoChiTiet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Date;
import java.util.*;

@WebServlet("/quanly/nhaphang")
public class NhapHangServlet extends HttpServlet {

    private PhieuNhapKhoDAOImpl dao = new PhieuNhapKhoDAOImpl();
    private PhieuNhapKhoChiTietDAOImpl ctDAO = new PhieuNhapKhoChiTietDAOImpl();

    // ================= GET =================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Map<String, Object>> viewList = new ArrayList<>();

        try {

            String action = request.getParameter("action");
            String idStr = request.getParameter("id");

            // ===== DETAIL =====
            if ("detail".equals(action) && idStr != null) {

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

            // ===== LIST =====
            List<PhieuNhapKho> list = dao.findAll();

            for (PhieuNhapKho p : list) {

                Map<String, Object> map = new HashMap<>();

                map.put("maPhieu", p.getMaPhieuNhapKho());
                map.put("ngayNhap", p.getNgayNhapKho());
                map.put("tongTien", p.getTongTien());
                map.put("nhanVien", dao.getTenNhanVien(p.getMaNguoiDung()));
                map.put("ncc", dao.getTenNCC(p.getMaNCC()));
                map.put("ghiChu", p.getGhiChu()); // FIX

                viewList.add(map);
            }

            request.setAttribute("nccList", dao.getAllNCC());
            request.setAttribute("nlList", dao.getAllNguyenLieu());

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("list", viewList);
        request.getRequestDispatcher("/nhaphang.jsp").forward(request, response);
    }

    // ================= POST =================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int maNguoiDung = Integer.parseInt(request.getParameter("maNguoiDung"));
            int maNCC = Integer.parseInt(request.getParameter("maNCC"));
            String ghiChu = request.getParameter("ghiChu"); // FIX

            // ===== TẠO PHIẾU =====
            PhieuNhapKho pn = new PhieuNhapKho();
            pn.setMaNguoiDung(maNguoiDung);
            pn.setMaNCC(maNCC);
            pn.setNgayNhapKho(new java.sql.Timestamp(System.currentTimeMillis()));
            pn.setTongTien(0);
            pn.setGhiChu(ghiChu);

            int maPN = dao.insert(pn);

            // ===== LẤY ARRAY =====
            String[] maNLs = request.getParameterValues("maNguyenLieu");
            String[] soLuongs = request.getParameterValues("soLuong");
            String[] donGias = request.getParameterValues("donGia");
            String[] hsds = request.getParameterValues("hsd");

            long tongTien = 0; // FIX chống tràn

            for (int i = 0; i < maNLs.length; i++) {

                int maNL = Integer.parseInt(maNLs[i]);
                int soLuong = Integer.parseInt(soLuongs[i]);
                int donGia = Integer.parseInt(donGias[i]);

                Date hsd = (hsds[i] == null || hsds[i].isEmpty())
                        ? null
                        : Date.valueOf(hsds[i]);

                PhieuNhapKhoChiTiet ct = new PhieuNhapKhoChiTiet();

                ct.setMaPhieuNhapKho(maPN);
                ct.setMaNguyenLieu(maNL);
                ct.setSoLuong(soLuong);
                ct.setDonGiaNhap(donGia);
                ct.setNgayHetHan(hsd);

                ctDAO.insert(ct);

                tongTien += (long) soLuong * donGia; // FIX
            }

            // ===== UPDATE =====
            dao.updateTongTien(maPN, (int) tongTien);

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("nhaphang");
    }
}