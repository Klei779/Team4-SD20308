package test;

import dao.NguyenLieuDAO;
import dao.NguyenLieuDAOImpl;
import entity.NguyenLieu;

import java.util.List;

public class TestNguyenLieuDAO {

    public static void main(String[] args) {

        NguyenLieuDAO dao = new NguyenLieuDAOImpl();

        // ===================== TEST INSERT =====================
        NguyenLieu nl = new NguyenLieu();
        nl.setMaLoaiNguyenLieu(1);
        nl.setTenNguyenLieu("NguyenLieu Test");
        nl.setSoLuongTon(100);
        nl.setDonVi("ml");
        nl.setSoLuongToiThieu(10);
        nl.setGhiChu("Test insert");

        dao.insert(nl);
        System.out.println("Thêm nguyên liệu thành công!");

        // ===================== TEST FIND ALL =====================
        System.out.println("\n--- DANH SÁCH NGUYÊN LIỆU ---");
        List<NguyenLieu> list = dao.findAll();
        for (NguyenLieu item : list) {
            System.out.println(
                    item.getMaNguyenLieu() + " | " +
                            item.getTenNguyenLieu() + " | " +
                            item.getSoLuongTon()
            );
        }

        // ===================== TEST FIND BY TÊN =====================
        System.out.println("\n--- TÌM THEO TÊN ---");
        List<NguyenLieu> listByTen = dao.findByTen("Test");
        for (NguyenLieu item : listByTen) {
            System.out.println(
                    item.getMaNguyenLieu() + " | " +
                            item.getTenNguyenLieu()
            );
        }

        // ===================== TEST FIND BY LOẠI =====================
        System.out.println("\n--- TÌM THEO LOẠI ---");
        List<NguyenLieu> listByLoai = dao.findByLoai(1);
        for (NguyenLieu item : listByLoai) {
            System.out.println(
                    item.getMaNguyenLieu() + " | " +
                            item.getTenNguyenLieu()
            );
        }

        // ===================== TEST UPDATE =====================
        if (!listByTen.isEmpty()) {
            NguyenLieu updateNL = listByTen.get(0);

            updateNL.setTenNguyenLieu("NguyenLieu Updated");

            dao.update(updateNL);
            System.out.println("\nCập nhật thành công!");
        } else {
            System.out.println("\nKhông có dữ liệu để update!");
        }
    }
}