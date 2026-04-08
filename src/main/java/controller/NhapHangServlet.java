package controller;

import dao.*;
import entity.*;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@WebServlet("/quanly/nhaphang")
public class NhapHangServlet extends HttpServlet {
    private NhaCungCapDAOImpl nccDAO = new NhaCungCapDAOImpl();
    private PhieuNhapKhoDAOImpl dao = new PhieuNhapKhoDAOImpl();
    private PhieuNhapKhoChiTietDAOImpl ctDAO = new PhieuNhapKhoChiTietDAOImpl();
    private NguyenLieuDAOImpl nlDAO = new NguyenLieuDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ===== 1. Lấy danh sách phiếu nhập =====
        List<PhieuNhapKho> list = dao.findAll();

        // Map tên nhân viên và NCC
        Map<Integer, String> mapNhanVien = new HashMap<>();
        Map<Integer, String> mapNCC = new HashMap<>();
        for (PhieuNhapKho p : list) {
            int maND = p.getMaNguoiDung();
            int maNCC = p.getMaNCC();

            if (!mapNhanVien.containsKey(maND)) {
                mapNhanVien.put(maND, dao.getTenNhanVien(maND));
            }
            if (!mapNCC.containsKey(maNCC)) {
                mapNCC.put(maNCC, nccDAO.findById(maNCC).getTenNhaCungCap());
            }
        }

        request.setAttribute("list", list);
        request.setAttribute("mapNhanVien", mapNhanVien);
        request.setAttribute("mapNCC", mapNCC);

        // ===== 2. Xử lý action xem chi tiết phiếu nhập =====
        String action = request.getParameter("action");
        if ("detail".equals(action)) {
            try {
                int phieuId = Integer.parseInt(request.getParameter("id"));
                PhieuNhapKho phieu = dao.findById(phieuId);
                if (phieu != null) {
                    List<PhieuNhapKhoChiTiet> dsCT = ctDAO.findByPhieuNhapKho(phieuId);
                    if (dsCT == null) dsCT = new ArrayList<>();

                    List<Map<String,Object>> ctList = new ArrayList<>();
                    for (PhieuNhapKhoChiTiet ct : dsCT) {
                        Map<String,Object> item = new HashMap<>();
                        item.put("tenNL", ctDAO.getTenNguyenLieu(ct.getMaNguyenLieu()));
                        item.put("soLuong", ct.getSoLuong());
                        item.put("donGia", ct.getDonGiaNhap());
                        item.put("thanhTien", ct.getSoLuong() * ct.getDonGiaNhap());
                        ctList.add(item);
                    }

                    request.setAttribute("ctList", ctList);
                    request.setAttribute("maPhieu", phieu.getMaPhieuNhapKho());
                    request.setAttribute("nhanVien", dao.getTenNhanVien(phieu.getMaNguoiDung()));
                    request.setAttribute("ncc", nccDAO.findById(phieu.getMaNCC()).getTenNhaCungCap());
                    request.setAttribute("ngayNhap", phieu.getNgayNhapKho());
                    request.setAttribute("tongTienHD", phieu.getTongTien());
                    request.setAttribute("openModal", true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // ===== 3. Dữ liệu combo box tạo phiếu nhập mới =====
        request.setAttribute("nlList", nlDAO.findAll());        // danh sách nguyên liệu
        request.setAttribute("nccList", nccDAO.findAll());      // danh sách NCC chuẩn entity

        // ===== 4. Forward sang JSP =====
        request.getRequestDispatcher("/nhaphang.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int maNCC = Integer.parseInt(request.getParameter("maNCC"));
            String ghiChu = request.getParameter("ghiChu");

            String[] maNL = request.getParameterValues("maNL[]");
            String[] soLuong = request.getParameterValues("soLuong[]");
            String[] donGia = request.getParameterValues("donGia[]");
            String[] hansd = request.getParameterValues("hansd[]");

            Date now = new Date(); // java.util.Date

            // ===== 1. Tạo phiếu nhập =====
            PhieuNhapKho phieu = new PhieuNhapKho();
            phieu.setMaNCC(maNCC);
            phieu.setGhiChu(ghiChu);
            phieu.setNgayNhapKho(new Timestamp(now.getTime()));
            phieu.setMaNguoiDung(1); // demo

            // ===== 2. Tính tổng tiền =====
            int tongTien = 0;
            for (int i = 0; i < maNL.length; i++) {
                int sl = Integer.parseInt(soLuong[i]);
                int dg = Integer.parseInt(donGia[i]);
                tongTien += sl * dg;
            }
            phieu.setTongTien(tongTien);

            // ===== 3. Lưu phiếu nhập và lấy ID =====
            int maPhieu = dao.insert(phieu); // giả sử insert trả về ID tự sinh

            // ===== 4. Lưu chi tiết phiếu nhập và cập nhật kho =====
            for (int i = 0; i < maNL.length; i++) {
                int maNguyenLieu = Integer.parseInt(maNL[i]);
                int sl = Integer.parseInt(soLuong[i]);
                int dg = Integer.parseInt(donGia[i]);

                // Lưu chi tiết
                PhieuNhapKhoChiTiet ct = new PhieuNhapKhoChiTiet();
                ct.setMaPhieuNhapKho(maPhieu);
                ct.setMaNguyenLieu(maNguyenLieu);
                ct.setSoLuong(sl);
                ct.setDonGiaNhap(dg);
                ctDAO.insert(ct);

                // ===== Cập nhật số lượng kho =====
                NguyenLieu nl = nlDAO.findById(maNguyenLieu);
                nl.setSoLuongTon(nl.getSoLuongTon() + sl);
                nlDAO.update(nl);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/quanly/nhaphang");
    }
}