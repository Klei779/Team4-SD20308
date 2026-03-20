package test;


import dao.ThongKeDAO;
import dao.ThongKeDAOImpl;
import entity.ThongKeDTO;
import entity.ThongKeDoUongDTO;
import entity.ThongKeNhanVienDTO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestThongKeDAO {
    public static void main(String[] args) throws Exception {

        ThongKeDAO dao = new ThongKeDAOImpl();

        // 👉 Tạo khoảng thời gian test
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fromDate = sdf.parse("2026-03-20 10:00:56.310");
        Date toDate = sdf.parse("2026-03-21 10:00:56.310");

        // =========================
        // ✅ 1. Test tổng thống kê
        // =========================
        ThongKeDTO tk = dao.getThongKe(fromDate, toDate);

        System.out.println("===== TỔNG THỐNG KÊ =====");
        System.out.println("Tổng hóa đơn: " + tk.getSoHoaDon());
        System.out.println("Tổng doanh thu: " + tk.getDoanhThu());
        System.out.println("Lợi nhuận: " + tk.getLoiNhuan());
        System.out.println("Tổng số hàng đã bán: " + tk.getTongSoLuong());

        // =========================
        // ✅ 2. Top đồ uống
        // =========================
        List<ThongKeDoUongDTO> listDU = dao.getTopDoUong(fromDate, toDate);

        System.out.println("\n===== TOP ĐỒ UỐNG =====");
        for (ThongKeDoUongDTO du : listDU) {
            System.out.println(du.getTenDoUong() + " - SL: " + du.getSoLuong());
        }

        // =========================
        // ✅ 3. Doanh thu nhân viên
        // =========================
        List<ThongKeNhanVienDTO> listNV = dao.getDoanhThuNhanVien(fromDate, toDate);

        System.out.println("\n===== DOANH THU NHÂN VIÊN =====");
        for (ThongKeNhanVienDTO nv : listNV) {
            System.out.println(nv.getTenNhanVien() + " - Doanh thu: " + nv.getDoanhThu());
        }

        System.out.println("\n🎉 Test Thống Kê OK!");
    }
}