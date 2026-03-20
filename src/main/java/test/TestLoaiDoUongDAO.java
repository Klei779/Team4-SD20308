package test;

import dao.LoaiDoUongDAOImpl;
import entity.LoaiDoUong;

import java.util.List;

public class TestLoaiDoUongDAO {

    public static void main(String[] args) {

        LoaiDoUongDAOImpl dao = new LoaiDoUongDAOImpl();

        // ===== 1. SELECT ALL =====
        System.out.println("=== DANH SÁCH ===");
        List<LoaiDoUong> list = dao.selectAll();
        for (LoaiDoUong l : list) {
            System.out.println(l.getMaLoai() + " - " + l.getTenLoai());
        }

        // ===== 2. SELECT BY ID =====
        System.out.println("\n=== TÌM ID = 1 ===");
        LoaiDoUong l1 = dao.selectById(1);
        if (l1 != null) {
            System.out.println(l1.getTenLoai());
        } else {
            System.out.println("Không tìm thấy");
        }

        // ===== 3. SELECT BY NAME =====
        System.out.println("\n=== TÌM THEO TÊN 'Trà' ===");
        List<LoaiDoUong> listByName = dao.selectByName("Trà");
        for (LoaiDoUong l : listByName) {
            System.out.println(l.getTenLoai());
        }

        // ===== 4. INSERT =====
        System.out.println("\n=== INSERT ===");
        LoaiDoUong newLoai = new LoaiDoUong();
        newLoai.setTenLoai("Test loại");

        boolean insert = dao.insert(newLoai);
        System.out.println("Insert: " + insert);

        // ===== 5. UPDATE =====
        System.out.println("\n=== UPDATE ===");
        LoaiDoUong updateLoai = dao.selectAll().get(0);
        updateLoai.setTenLoai("Updated loại");

        boolean update = dao.update(updateLoai);
        System.out.println("Update: " + update);

        // ===== 6. DELETE =====
        System.out.println("\n=== DELETE ===");

        // thêm mới rồi xoá cho chắc chắn
        LoaiDoUong temp = new LoaiDoUong();
        temp.setTenLoai("Temp delete");
        dao.insert(temp);

        List<LoaiDoUong> list2 = dao.selectAll();
        int lastId = list2.get(list2.size() - 1).getMaLoai();

        boolean delete = dao.delete(lastId);
        System.out.println("Delete: " + delete);

        System.out.println("\n=== DONE ===");
    }
}
