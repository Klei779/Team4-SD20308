package controller;

import dao.DoUongDAO;
import dao.DoUongDAOImpl;
import dao.CongThucDAO;
import dao.CongThucDAOImpl;
import entity.DoUong;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet({"/quanly/quanlydouong"})
@MultipartConfig(
)
public class QuanLyDoUongServlet extends HttpServlet {
    private DoUongDAO dao = new DoUongDAOImpl();
    private CongThucDAO ctDao = new CongThucDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Luôn load danh sách phụ trợ (Ví dụ: danh sách công thức để hiện trong Modal Add/Edit)
        request.setAttribute("dsCongThuc", ctDao.findall());

        // 2. Xử lý chức năng Xóa (nếu có tham số action=delete)
        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("maDoUong"));
                dao.delete(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Sau khi xóa, redirect về trang hiện tại để tránh lỗi duplicate request
            String ref = request.getHeader("Referer");
            response.sendRedirect(ref != null ? ref : request.getContextPath() + "/quanly/quanlydouong");
            return;
        }

        // 3. Tiếp nhận tham số Tìm kiếm & Lọc
        String txtSearch = request.getParameter("txtSearch");
        String loaiStr = request.getParameter("filterLoai");
        String statusStr = request.getParameter("filterStatus");

        // Ép kiểu an toàn (Dùng Object Integer/Boolean để chấp nhận giá trị null)
        Integer filterLoai = (loaiStr != null && !loaiStr.isEmpty()) ? Integer.valueOf(loaiStr) : null;
        Boolean filterStatus = (statusStr != null && !statusStr.isEmpty()) ? Boolean.valueOf(statusStr) : null;

        // 4. Tính toán Phân trang
        int page = 1;
        int pageSize = 10;
        try {
            String p = request.getParameter("page");
            if (p != null && !p.isEmpty()) page = Integer.parseInt(p);
        } catch (NumberFormatException e) {
            page = 1;
        }

        int offset = (page - 1) * pageSize;

        // 5. Gọi DAO lấy dữ liệu (Hàm gộp đa điều kiện)
        int totalRecords = dao.count(txtSearch, filterLoai, filterStatus);
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        // Nếu chẳng may page hiện tại > totalPages (do xóa bản ghi), reset về trang cuối
        if (page > totalPages && totalPages > 0) {
            page = totalPages;
            offset = (page - 1) * pageSize;
        }

        List<DoUong> list = dao.search(txtSearch, filterLoai, filterStatus, offset, pageSize);

        request.setAttribute("dsDoUong", list);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        request.setAttribute("searchValue", txtSearch);
        request.setAttribute("selectedLoai", loaiStr);
        request.setAttribute("selectedStatus", statusStr);

        request.getRequestDispatcher("/quanlydouong.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if ("save".equals(action)) {
            try {
                String idStr = request.getParameter("maDoUong");
                DoUong d = new DoUong();
                d.setTenDoUong(request.getParameter("tenDoUong"));
                d.setMaLoai(Integer.parseInt(request.getParameter("maLoai")));
                d.setMaCongThuc(Integer.parseInt(request.getParameter("maCongThuc")));
                d.setGiaTien(Integer.parseInt(request.getParameter("giaTien")));
                d.setMoTa(request.getParameter("moTa"));
                d.setTrangThai(request.getParameter("trangThai") != null);
                d.setGiaVon(BigDecimal.ZERO);
                d.setKhuyenMai(BigDecimal.ZERO);

                Part filePart = request.getPart("hinhAnhFile");
                String fileName = filePart.getSubmittedFileName();

                if (fileName != null && !fileName.isEmpty()) {

                    String uploadPath = getServletContext().getRealPath("/uploads");
                    File uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) uploadDir.mkdir();

                    filePart.write(uploadPath + File.separator + fileName);
                    d.setHinhAnh(fileName);
                } else {

                    d.setHinhAnh(request.getParameter("hinhAnhOld"));
                }

                if (idStr == null || idStr.isEmpty()) {
                    dao.insert(d);
                } else {
                    d.setMaDoUong(Integer.parseInt(idStr));
                    dao.update(d);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        response.sendRedirect(request.getContextPath() + "/quanly/quanlydouong");
    }
}