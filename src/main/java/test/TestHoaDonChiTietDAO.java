package test;

import dao.HoaDonChiTietDAOImpl;
import entity.HoaDonChiTiet;

import java.util.List;

public class TestHoaDonChiTietDAO {

    public static void main(String[] args) {
        try {
            HoaDonChiTietDAOImpl dao = new HoaDonChiTietDAOImpl();

            // ===== 1. SELECT ALL =====
            System.out.println("=== DANH SÁCH HÓA ĐƠN CHI TIẾT ===");
            List<HoaDonChiTiet> list = dao.selectAll();
            for (HoaDonChiTiet hdct : list) {
                System.out.println(
                        "HDCT: " + hdct.getMaHDCT() +
                                " | HĐ: " + hdct.getMaHoaDon() +
                                " | Đồ uống: " + hdct.getMaDoUong() +
                                " | SL: " + hdct.getSoLuong() +
                                " | Giá: " + hdct.getDonGia()
                );
            }

            // ===== 2. SELECT THEO MÃ HÓA ĐƠN =====
            System.out.println("\n=== CHI TIẾT HÓA ĐƠN ID = 1 ===");
            List<HoaDonChiTiet> listByHD = dao.selectByHoaDonId(1);
            for (HoaDonChiTiet hdct : listByHD) {
                System.out.println(
                        "Đồ uống ID: " + hdct.getMaDoUong() +
                                " | SL: " + hdct.getSoLuong()
                );
            }

            // ===== 3. LẤY TÊN ĐỒ UỐNG =====
            System.out.println("\n=== TÊN ĐỒ UỐNG ID = 1 ===");
            String ten = dao.getTenDoUong(1);
            System.out.println("Tên: " + ten);

            System.out.println("\n=== DONE ===");

        } catch (Exception e) {
            e.printStackTrace(); // hiện lỗi chi tiết nếu có
        }
    }
}