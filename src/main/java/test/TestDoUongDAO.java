package test;

import dao.DoUongDAO;
import dao.DoUongDAOImpl;
import entity.DoUong;

import java.math.BigDecimal;
import java.util.List;

public class TestDoUongDAO {

    public static void main(String[] args) {

        DoUongDAO dao = new DoUongDAOImpl();

        // ===================== TEST INSERT =====================
        DoUong d = new DoUong();
        d.setMaLoai(1);        // phải tồn tại
        d.setMaCongThuc(1);    // phải tồn tại
        d.setTenDoUong("Trà sữa test");
        d.setGiaTien(25000);
        d.setGiaVon(new BigDecimal("15000"));
        d.setMoTa("Test đồ uống");
        d.setHinhAnh(null);
        d.setKhuyenMai(new BigDecimal("10"));
        d.setTrangThai(true);

        dao.insert(d);
        System.out.println("Thêm đồ uống thành công!");

        // ===================== TEST FIND ALL =====================
        System.out.println("\n--- DANH SÁCH ĐỒ UỐNG ---");
        List<DoUong> list = dao.findAll();
        for (DoUong item : list) {
            System.out.println(
                    item.getMaDoUong() + " | " +
                            item.getTenDoUong() + " | " +
                            item.getGiaTien()
            );
        }

        // ===================== TEST FIND BY TRẠNG THÁI =====================
        System.out.println("\n--- TÌM THEO TRẠNG THÁI ---");
        List<DoUong> listByStatus = dao.findByTrangThai(true);
        for (DoUong item : listByStatus) {
            System.out.println(
                    item.getMaDoUong() + " | " +
                            item.getTenDoUong()
            );
        }

        // ===================== TEST FIND BY GIÁ =====================
        System.out.println("\n--- TÌM THEO GIÁ ---");
        List<DoUong> listByPrice = dao.findByGiaTien(25000);
        for (DoUong item : listByPrice) {
            System.out.println(
                    item.getMaDoUong() + " | " +
                            item.getTenDoUong()
            );
        }

        // ===================== TEST FIND BY TÊN =====================
        System.out.println("\n--- TÌM THEO TÊN ---");
        List<DoUong> listByName = dao.findByTenDoUong("Trà sữa test");
        for (DoUong item : listByName) {
            System.out.println(
                    item.getMaDoUong() + " | " +
                            item.getTenDoUong()
            );
        }

        // ===================== TEST FIND BY MÃ LOẠI =====================
        System.out.println("\n--- TÌM THEO MÃ LOẠI ---");
        List<DoUong> listByLoai = dao.findByMaLoai(1);
        for (DoUong item : listByLoai) {
            System.out.println(
                    item.getMaDoUong() + " | " +
                            item.getTenDoUong()
            );
        }

        // ===================== TEST UPDATE =====================
        if (!list.isEmpty()) {
            DoUong update = list.get(0);

            update.setTenDoUong("Đồ uống đã update");
            update.setGiaTien(30000);

            dao.update(update);
            System.out.println("\nCập nhật thành công!");
        }

        // ===================== TEST DELETE =====================
        List<DoUong> deleteList = dao.findByTenDoUong("Đồ uống đã update");

        if (!deleteList.isEmpty()) {
            int idDelete = deleteList.get(0).getMaDoUong();

            dao.delete(idDelete);
            System.out.println("\nXóa thành công!");
        } else {
            System.out.println("\nKhông có dữ liệu để xóa!");
        }
    }
}