package controller;

import dao.NguyenLieuDAOImpl;
import dao.ThongKeDAO;
import dao.ThongKeDAOImpl;
import entity.NguyenLieu;
import entity.ThongKeDTO;
import entity.ThongKeDoUongDTO;
import entity.NgayDTO;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.*;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private final ThongKeDAO thongKeDAO = new ThongKeDAOImpl();
    private final NguyenLieuDAOImpl nguyenLieuDAO = new NguyenLieuDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            // ================== HÔM NAY ==================
            Calendar cal = Calendar.getInstance();

            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            Date fromDate = cal.getTime();

            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            Date toDate = cal.getTime();

            ThongKeDTO tk = thongKeDAO.getThongKe(fromDate, toDate);

            request.setAttribute("revenueToday", tk != null ? tk.getDoanhThu() : 0);
            request.setAttribute("ordersToday", tk != null ? tk.getSoHoaDon() : 0);
            request.setAttribute("pendingBills", 0);
            request.setAttribute("activeStaff", 0);

            // ================== TOP ĐỒ UỐNG ==================
            List<ThongKeDoUongDTO> topList =
                    thongKeDAO.getTopDoUong(fromDate, toDate);

            request.setAttribute("topList", topList);

            // ================== BIỂU ĐỒ 7 NGÀY ==================
            Calendar calChart = Calendar.getInstance();

            Date toChart = calChart.getTime();
            calChart.add(Calendar.DAY_OF_MONTH, -6);
            Date fromChart = calChart.getTime();

            List<NgayDTO> listNgay =
                    thongKeDAO.getDoanhThuTheoNgay(fromChart, toChart);

            // tìm max
            int max = 0;
            for (NgayDTO n : listNgay) {
                if (n.getDoanhThu() > max) max = n.getDoanhThu();
            }
            if (max == 0) max = 1;

            // convert %
            List<Integer> percents = new ArrayList<>();
            for (NgayDTO n : listNgay) {
                percents.add((int) (n.getDoanhThu() * 100.0 / max));
            }

            request.setAttribute("days", listNgay);
            request.setAttribute("percents", percents);

            // ================== HÓA ĐƠN GẦN ĐÂY (FAKE) ==================
            List<Map<String, Object>> billList = new ArrayList<>();

            for (int i = 0; i < 5; i++) {
                Map<String, Object> b = new HashMap<>();
                b.put("maHoaDon", "HD" + (1340 - i));
                b.put("tongTien", (i + 1) * 50000);
                billList.add(b);
            }

            request.setAttribute("billList", billList);

            // ================== NGUYÊN LIỆU SẮP HẾT ==================
            List<NguyenLieu> listNL = nguyenLieuDAO.findAll();
            List<NguyenLieu> sapHet = new ArrayList<>();

            for (NguyenLieu nl : listNL) {
                if (nl.getSoLuongTon() < nl.getSoLuongToiThieu()) {
                    sapHet.add(nl);
                }
            }

            request.setAttribute("lowStockList", sapHet);

            // hiển thị 1 cái đầu (nếu cần)
            if (!sapHet.isEmpty()) {
                NguyenLieu nl = sapHet.get(0);
                request.setAttribute("lowStockName", nl.getTenNguyenLieu());
                request.setAttribute("lowStockAmount", nl.getSoLuongTon());
                request.setAttribute("lowStockThreshold", nl.getSoLuongToiThieu());
            } else {
                request.setAttribute("lowStockName", "Không có");
                request.setAttribute("lowStockAmount", 0);
                request.setAttribute("lowStockThreshold", 0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // ================== FORWARD ==================
        request.getRequestDispatcher("/dashboard.jsp")
                .forward(request, response);
    }
}