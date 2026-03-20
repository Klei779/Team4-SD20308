package test;

import dao.PhieuNhapKhoDAO;
import dao.PhieuNhapKhoDAOImpl;
import entity.PhieuNhapKho;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class TestPhieuNhapKhoDAO {

    public static void main(String[] args) {

        PhieuNhapKhoDAO dao = new PhieuNhapKhoDAOImpl();

        // ===================== TEST INSERT =====================
        PhieuNhapKho pnk = new PhieuNhapKho();
        pnk.setMaNguoiDung(1);
        pnk.setMaNCC(1);
        pnk.setNgayNhapKho(new Timestamp(System.currentTimeMillis()));
        pnk.setTongTien(150000);
        pnk.setGhiChu("Test insert");

        dao.insert(pnk);
        System.out.println("Thêm phiếu nhập kho thành công!");

        // ===================== TEST FIND ALL =====================
        System.out.println("\n--- DANH SÁCH PHIẾU NHẬP KHO ---");
        List<PhieuNhapKho> list = dao.findAll();
        for (PhieuNhapKho p : list) {
            System.out.println(
                    p.getMaPhieuNhapKho() + " | " +
                            p.getMaNguoiDung() + " | " +
                            p.getMaNCC() + " | " +
                            p.getNgayNhapKho()
            );
        }

        // ===================== TEST FIND BY DATE =====================
        System.out.println("\n--- TÌM THEO NGÀY ---");

        Date from = new Date(System.currentTimeMillis() - (7L * 24 * 60 * 60 * 1000)); // 7 ngày trước
        Date to = new Date();

        List<PhieuNhapKho> listByDate = dao.findByDate(from, to);
        for (PhieuNhapKho p : listByDate) {
            System.out.println(
                    p.getMaPhieuNhapKho() + " | " +
                            p.getNgayNhapKho()
            );
        }

        // ===================== TEST FIND BY NGƯỜI DÙNG =====================
        System.out.println("\n--- TÌM THEO NGƯỜI DÙNG ---");
        List<PhieuNhapKho> listByUser = dao.findByNguoiDung(1);
        for (PhieuNhapKho p : listByUser) {
            System.out.println(
                    p.getMaPhieuNhapKho() + " | User: " +
                            p.getMaNguoiDung()
            );
        }

        // ===================== TEST FIND BY NCC =====================
        System.out.println("\n--- TÌM THEO NHÀ CUNG CẤP ---");
        List<PhieuNhapKho> listByNCC = dao.findByNCC(1);
        for (PhieuNhapKho p : listByNCC) {
            System.out.println(
                    p.getMaPhieuNhapKho() + " | NCC: " +
                            p.getMaNCC()
            );
        }
    }
}