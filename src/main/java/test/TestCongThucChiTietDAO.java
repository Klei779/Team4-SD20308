package test;

import dao.CongThucCTDAO;
import dao.CongThucCTDAOImpl;
import entity.CongThucChiTiet;

import java.util.List;

public class TestCongThucChiTietDAO {

    public static void main(String[] args) {

        CongThucCTDAO dao = new CongThucCTDAOImpl();

        // ===================== TEST INSERT =====================
        CongThucChiTiet ctct = new CongThucChiTiet();
        ctct.setMaCongThuc(1);     // phải tồn tại trong DB
        ctct.setMaNguyenLieu(1);   // phải tồn tại trong DB
        ctct.setDinhLuong(110);

        dao.insert(ctct);
        System.out.println("Thêm công thức chi tiết thành công!");

        // ===================== TEST FIND ALL =====================
        System.out.println("\n--- DANH SÁCH CTCT ---");
        List<CongThucChiTiet> list = dao.findAll();
        for (CongThucChiTiet c : list) {
            System.out.println(
                    c.getMaCTCT() + " | " +
                            c.getMaCongThuc() + " | " +
                            c.getMaNguyenLieu() + " | " +
                            c.getDinhLuong()
            );
        }

        // ===================== TEST FIND BY CÔNG THỨC =====================
        System.out.println("\n--- TÌM THEO CÔNG THỨC ---");
        List<CongThucChiTiet> listByCT = dao.findByCongThuc(1);
        for (CongThucChiTiet c : listByCT) {
            System.out.println(
                    c.getMaCTCT() + " | " +
                            c.getMaNguyenLieu() + " | " +
                            c.getDinhLuong()
            );
        }

        // ===================== TEST UPDATE =====================
        if (!list.isEmpty()) {
            CongThucChiTiet updateCT = list.get(0);

            updateCT.setDinhLuong(999);

            dao.update(updateCT);
            System.out.println("\nCập nhật thành công!");
        }

        // ===================== TEST DELETE =====================
        List<CongThucChiTiet> deleteList = dao.findByCongThuc(1);

        if (!deleteList.isEmpty()) {
            int idDelete = deleteList.get(deleteList.size() - 1).getMaCTCT();

            dao.delete(idDelete);
            System.out.println("\nXóa thành công!");
        } else {
            System.out.println("\nKhông có dữ liệu để xóa!");
        }
    }
}