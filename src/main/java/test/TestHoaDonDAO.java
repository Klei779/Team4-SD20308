package test;

import dao.HoaDonDAOImpl;
import entity.HoaDon;

import java.sql.Timestamp;
import java.util.List;

public class TestHoaDonDAO {

    public static void main(String[] args) {

        HoaDonDAOImpl dao = new HoaDonDAOImpl();

        // ===== 1. SELECT ALL =====
        System.out.println("=== DANH SÁCH HÓA ĐƠN ===");
        List<HoaDon> list = dao.selectAll();
        for (HoaDon hd : list) {
            System.out.println(
                    hd.getMaHoaDon() + " | NV: " + hd.getMaNguoiDung() +
                            " | Tổng tiền: " + hd.getTongTien() +
                            " | Ngày: " + hd.getNgayTao() +
                            " | Trạng thái: " + hd.isTrangThai()
            );
        }

        // ===== 2. SELECT THEO MÃ NGƯỜI DÙNG =====
        System.out.println("\n=== HÓA ĐƠN CỦA NGƯỜI DÙNG ID = 1 ===");
        List<HoaDon> listByUser = dao.selectByMaNguoiDung(1);
        for (HoaDon hd : listByUser) {
            System.out.println("HD: " + hd.getMaHoaDon() + " - " + hd.getTongTien());
        }

        // ===== 3. SELECT THEO TRẠNG THÁI =====
        System.out.println("\n=== HÓA ĐƠN TRẠNG THÁI = true ===");
        List<HoaDon> listByStatus = dao.selectByTrangThai(true);
        for (HoaDon hd : listByStatus) {
            System.out.println("HD: " + hd.getMaHoaDon());
        }

        // ===== 4. SELECT THEO NGÀY =====
        System.out.println("\n=== HÓA ĐƠN TRONG KHOẢNG NGÀY ===");

        Timestamp from = Timestamp.valueOf("2025-01-01 00:00:00");
        Timestamp to = new Timestamp(System.currentTimeMillis());

        List<HoaDon> listByDate = dao.selectByDate(from, to);
        for (HoaDon hd : listByDate) {
            System.out.println("HD: " + hd.getMaHoaDon() + " | Ngày: " + hd.getNgayTao());
        }

        // ===== 5. LẤY TÊN NHÂN VIÊN =====
        System.out.println("\n=== TÊN NHÂN VIÊN ID = 1 ===");
        String tenNV = dao.getTenNhanVien(1);
        System.out.println("Tên: " + tenNV);

        System.out.println("\n=== DONE ===");
    }
}
