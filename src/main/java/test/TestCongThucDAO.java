package test;

import dao.CongThucDAO;
import dao.CongThucDAOImpl;
import entity.CongThuc;

import java.util.List;

public class TestCongThucDAO {

    public static void main(String[] args) {

        CongThucDAO dao = new CongThucDAOImpl();

        // ===================== TEST INSERT =====================
        CongThuc ct = new CongThuc();
        ct.setTenCongThuc("Trà sữa test");

        dao.insert(ct);
        System.out.println("Thêm công thức thành công!");

        // ===================== TEST FIND ALL =====================
        System.out.println("\n--- DANH SÁCH CÔNG THỨC ---");
        List<CongThuc> list = dao.findall();
        for (CongThuc c : list) {
            System.out.println(
                    c.getMaCongThuc() + " | " +
                            c.getTenCongThuc()
            );
        }

        // ===================== TEST FIND BY ID =====================
        if (!list.isEmpty()) {
            int id = list.get(0).getMaCongThuc();

            List<CongThuc> foundList = dao.findByCongThuc(id);

            System.out.println("\n--- FIND BY ID ---");
            for (CongThuc c : foundList) {
                System.out.println(
                        c.getMaCongThuc() + " | " +
                                c.getTenCongThuc()
                );
            }
        }

        // ===================== TEST UPDATE =====================
        if (!list.isEmpty()) {
            CongThuc updateCT = list.get(0);

            updateCT.setTenCongThuc("Công thức đã update");

            dao.update(updateCT);
            System.out.println("\nCập nhật thành công!");
        }

        // ===================== TEST DELETE =====================
        List<CongThuc> deleteList = dao.findall();

        if (!deleteList.isEmpty()) {
            CongThuc deleteCT = deleteList.get(deleteList.size() - 1);

            dao.delete(deleteCT);
            System.out.println("\nXóa thành công!");
        } else {
            System.out.println("\nKhông có dữ liệu để xóa!");
        }
    }
}