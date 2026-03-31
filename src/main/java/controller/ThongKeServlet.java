package controller;

import dao.ThongKeDAO;
import dao.ThongKeDAOImpl;
import entity.NgayDTO;
import entity.ThongKeDTO;
import entity.ThongKeDoUongDTO;
import entity.ThongKeNhanVienDTO;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.Date;

@WebServlet("/quanly/thongke")
public class ThongKeServlet extends HttpServlet {

    private ThongKeDAO thongKeDAO = new ThongKeDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String range = request.getParameter("range");

            Date now = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(now);

            Date fromDate;

            // ===== XÁC ĐỊNH fromDate =====
            if ("today".equals(range)) {
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
                fromDate = cal.getTime();

            } else if ("30days".equals(range)) {
                cal.add(Calendar.DAY_OF_MONTH, -30);
                fromDate = cal.getTime();

            } else {
                cal.add(Calendar.DAY_OF_MONTH, -7);
                fromDate = cal.getTime();
                range = "7days";
            }

            // ===== FIX toDate = đầu ngày hôm sau =====
            Calendar calTo = Calendar.getInstance();
            calTo.setTime(now);
            calTo.set(Calendar.HOUR_OF_DAY, 0);
            calTo.set(Calendar.MINUTE, 0);
            calTo.set(Calendar.SECOND, 0);
            calTo.set(Calendar.MILLISECOND, 0);
            calTo.add(Calendar.DAY_OF_MONTH, 1);

            Date toDate = calTo.getTime();

            // ===== GỌI DAO (bị sai doanh thu) =====
            ThongKeDTO tk = thongKeDAO.getThongKe(fromDate, toDate);

            // ===== FIX DOANH THU (KHÔNG JOIN) =====
            int doanhThuDung = getDoanhThuDung(fromDate, toDate);
            tk.setDoanhThu(doanhThuDung);

            // ===== LOAD DỮ LIỆU KHÁC =====
            List<ThongKeDoUongDTO> topDoUong = thongKeDAO.getTopDoUong(fromDate, toDate);
            List<ThongKeNhanVienDTO> nhanVien = thongKeDAO.getDoanhThuNhanVien(fromDate, toDate);

            // ===== SET ATTRIBUTE (SAU KHI FIX) =====
            request.setAttribute("tk", tk);
            request.setAttribute("topDoUong", topDoUong);
            request.setAttribute("nhanVien", nhanVien);
            request.setAttribute("range", range);

            List<NgayDTO> listNgay = thongKeDAO.getDoanhThuTheoNgay(fromDate, toDate);
            request.setAttribute("listNgay", listNgay);

            request.getRequestDispatcher("/thongke.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi thống kê!");
        }
    }

    // ===== HÀM LẤY DOANH THU CHUẨN (KHÔNG JOIN) =====
    private int getDoanhThuDung(Date fromDate, Date toDate) {
        String sql = "SELECT ISNULL(SUM(tongTien),0) FROM HoaDon WHERE ngayTao >= ? AND ngayTao < ?";

        try (Connection conn = util.JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, new Timestamp(fromDate.getTime()));
            ps.setTimestamp(2, new Timestamp(toDate.getTime()));

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}