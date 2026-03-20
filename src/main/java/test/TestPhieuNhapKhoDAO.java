package test;

import dao.PhieuNhapKhoDAO;
import dao.PhieuNhapKhoDAOImpl;
import entity.PhieuNhapKho;

import java.sql.Timestamp;
import java.util.List;

public class TestPhieuNhapKhoDAO {
    public static void main(String[] args) {
        PhieuNhapKhoDAO dao = new PhieuNhapKhoDAOImpl();

        // Test thêm
        PhieuNhapKho pnk = new PhieuNhapKho();
        pnk.setMaNguoiDung(1);
        pnk.setMaNCC(1);
        pnk.setNgayNhapKho(Timestamp.valueOf("2027-01-01 00:00:00"));
        pnk.setGhiChu("");
        pnk.setTongTien(100000);

        dao.insert(pnk);
        System.out.println("Thêm thành công!");

        // Test lấy dữ liệu
        List<PhieuNhapKho> list = dao.findAll();
        for (PhieuNhapKho p : list) {
            System.out.println(p.getMaPhieuNhapKho());
        }
    }
}