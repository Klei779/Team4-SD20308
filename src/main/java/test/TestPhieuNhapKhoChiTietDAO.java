package test;

import dao.PhieuNhapKhoChiTietDAO;
import dao.PhieuNhapKhoChiTietDAOImpl;
import entity.PhieuNhapKhoChiTiet;

import java.sql.Date;
import java.util.List;

public class TestPhieuNhapKhoChiTietDAO {

    public static void main(String[] args) {

        PhieuNhapKhoChiTietDAO dao = new PhieuNhapKhoChiTietDAOImpl();

        // ===================== TEST INSERT =====================
        PhieuNhapKhoChiTiet ct = new PhieuNhapKhoChiTiet();
        ct.setMaPhieuNhapKho(1);   // phải tồn tại
        ct.setMaNguyenLieu(1);     // phải tồn tại
        ct.setSoLuong(50);
        ct.setNgayHetHan(Date.valueOf("2027-12-31"));
        ct.setDonGiaNhap(10000);

        dao.insert(ct);
        System.out.println("Thêm chi tiết phiếu nhập thành công!");

        // ===================== TEST FIND ALL =====================
        System.out.println("\n--- DANH SÁCH CHI TIẾT ---");
        List<PhieuNhapKhoChiTiet> list = dao.findAll();
        for (PhieuNhapKhoChiTiet item : list) {
            System.out.println(
                    item.getMaPhieuNhapKhoCT() + " | " +
                            item.getMaPhieuNhapKho() + " | " +
                            item.getMaNguyenLieu() + " | " +
                            item.getSoLuong()
            );
        }

        // ===================== TEST FIND BY PHIẾU NHẬP =====================
        System.out.println("\n--- TÌM THEO PHIẾU NHẬP ---");
        List<PhieuNhapKhoChiTiet> listByPNK = dao.findByPhieuNhapKho(1);
        for (PhieuNhapKhoChiTiet item : listByPNK) {
            System.out.println(
                    item.getMaPhieuNhapKhoCT() + " | NL: " +
                            item.getMaNguyenLieu() + " | SL: " +
                            item.getSoLuong()
            );
        }

        // ===================== TEST GET TÊN NGUYÊN LIỆU =====================
        System.out.println("\n--- LẤY TÊN NGUYÊN LIỆU ---");
        String tenNL = dao.getTenNguyenLieu(1);
        System.out.println("Tên nguyên liệu ID=1: " + tenNL);
    }
}