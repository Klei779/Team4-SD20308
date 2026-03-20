package test;

import dao.NhaCungCapDAO;
import dao.NhaCungCapDAOImpl;
import entity.NhaCungCap;

import java.util.List;

public class TestNhaCungCapDAO {

    public static void main(String[] args) {

        NhaCungCapDAO dao = new NhaCungCapDAOImpl();

        // ===================== TEST INSERT =====================
        NhaCungCap ncc = new NhaCungCap();
        ncc.setTenNhaCungCap("NCC Test");
        ncc.setDienThoai("0123456789");
        ncc.setDiaChi("TP.HCM");

        dao.insert(ncc);
        System.out.println("Thêm nhà cung cấp thành công!");

        // ===================== TEST FIND ALL =====================
        System.out.println("\n--- DANH SÁCH NCC ---");
        List<NhaCungCap> list = dao.findall();
        for (NhaCungCap item : list) {
            System.out.println(
                    item.getMaNhaCungCap() + " | " +
                            item.getTenNhaCungCap() + " | " +
                            item.getDienThoai()
            );
        }

        // ===================== TEST FIND BY NAME =====================
        System.out.println("\n--- TÌM THEO TÊN ---");
        List<NhaCungCap> listByName = dao.findByName("NCC Test");
        for (NhaCungCap item : listByName) {
            System.out.println(
                    item.getMaNhaCungCap() + " | " +
                            item.getTenNhaCungCap()
            );
        }

        // ===================== TEST UPDATE =====================
        if (!list.isEmpty()) {
            NhaCungCap update = list.get(0);

            update.setTenNhaCungCap("NCC Updated");
            update.setDiaChi("Hà Nội");

            dao.update(update);
            System.out.println("\nCập nhật thành công!");
        }

        // ===================== TEST DELETE =====================
        List<NhaCungCap> deleteList = dao.findByName("Updated");

        if (!deleteList.isEmpty()) {
            NhaCungCap deleteItem = deleteList.get(0);

            dao.delete(deleteItem);
            System.out.println("\nXóa thành công!");
        } else {
            System.out.println("\nKhông có dữ liệu để xóa!");
        }
    }
}