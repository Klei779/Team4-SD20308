package controller;

import dao.DoUongDAO;
import dao.DoUongDAOImpl;
import dao.CongThucDAO;
import dao.CongThucDAOImpl;
import entity.DoUong;
import entity.CongThuc;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/quanly/quanlydouong")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class QuanLyDoUongServlet extends HttpServlet {
    private DoUongDAO dao = new DoUongDAOImpl();
    private CongThucDAO ctDao = new CongThucDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("dsCongThuc", ctDao.findall());

        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("maDoUong"));
            dao.delete(id);
            response.sendRedirect(request.getContextPath() + "/nhanvien/quanlydouong");
            return;
        }

        String txtSearch = request.getParameter("txtSearch");
        List<DoUong> list = (txtSearch != null && !txtSearch.isEmpty())
                ? dao.findByTenDoUong(txtSearch) : dao.findAll();

        request.setAttribute("dsDoUong", list);
        request.setAttribute("searchValue", txtSearch);
        request.getRequestDispatcher("/QuanLyDoUong.jsp").forward(request, response);
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