package controller;

import dao.CongThucCTDAO;
import dao.CongThucCTDAOImpl;
import entity.CongThuc;
import entity.CongThucChiTiet;
import dao.CongThucDAO;
import dao.CongThucDAOImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/nhanvien/quanlycongthuc")
public class QuanLyCongThucServlet extends HttpServlet {

    private final CongThucCTDAO ctctDAO = new CongThucCTDAOImpl();
     private final CongThucDAO ctDAO = new CongThucDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

         List<CongThuc> dsCT = ctDAO.findall();
         request.setAttribute("dsCT", dsCT);

        String maCTStr = request.getParameter("maCT");
        if (maCTStr != null && !maCTStr.isEmpty()) {
            try {
                int maCT = Integer.parseInt(maCTStr);

                // Gọi hàm findByCongThuc bạn đã viết trong DAOImpl
                List<CongThucChiTiet> chiTietMon = ctctDAO.findByCongThuc(maCT);

                // Đẩy dữ liệu chi tiết và mã công thức đang chọn sang JSP
                request.setAttribute("chiTietMon", chiTietMon);
                request.setAttribute("selectedMaCT", maCT);

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        request.getRequestDispatcher("/QuanLyCongThuc.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            int maCT = Integer.parseInt(request.getParameter("maCT"));
            int maNL = Integer.parseInt(request.getParameter("maNguyenLieu"));
            int dinhLuong = Integer.parseInt(request.getParameter("dinhLuong"));

            CongThucChiTiet ctct = new CongThucChiTiet();
            ctct.setMaCongThuc(maCT);
            ctct.setMaNguyenLieu(maNL);
            ctct.setDinhLuong(dinhLuong);

            ctctDAO.insert(ctct);

            response.sendRedirect("quanlycongthuc?maCT=" + maCT);

        } catch (Exception e) {
            e.printStackTrace();

            response.sendRedirect("quanlycongthuc");
        }
    }
}