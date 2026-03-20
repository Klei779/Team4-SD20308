package test;

import dao.NguoiDungDAO;
import dao.NguoiDungDAOImpl;
import entity.NguoiDung;

import java.util.List;

public class TestNguoiDungDAO {

    public static void main(String[] args) {

        NguoiDungDAO dao = new NguoiDungDAOImpl();

        // ===================== TEST INSERT =====================
        NguoiDung nd = new NguoiDung();
        nd.setTenNguoiDung("User Test");
        nd.setEmail("test@gmail.com");
        nd.setTenDangNhap("testuser");
        nd.setMatKhau("123");
        nd.setVaiTro("STAFF");
        nd.setTrangThai(true);
        nd.setHinhAnh(null);

        dao.insert(nd);
        System.out.println("Thêm người dùng thành công!");

        // ===================== TEST FIND ALL =====================
        System.out.println("\n--- DANH SÁCH NGƯỜI DÙNG ---");
        List<NguoiDung> list = dao.findAll();
        for (NguoiDung u : list) {
            System.out.println(
                    u.getMaNguoiDung() + " | " +
                            u.getTenNguoiDung() + " | " +
                            u.getVaiTro()
            );
        }

        // ===================== TEST FIND BY TÊN =====================
        System.out.println("\n--- TÌM THEO TÊN ---");
        List<NguoiDung> listByTen = dao.findByTen("User");
        for (NguoiDung u : listByTen) {
            System.out.println(
                    u.getMaNguoiDung() + " | " +
                            u.getTenNguoiDung()
            );
        }

        // ===================== TEST FIND BY TRẠNG THÁI =====================
        System.out.println("\n--- TÌM THEO TRẠNG THÁI ---");
        List<NguoiDung> listByStatus = dao.findByTrangThai(true);
        for (NguoiDung u : listByStatus) {
            System.out.println(
                    u.getMaNguoiDung() + " | " +
                            u.getTenNguoiDung()
            );
        }

        // ===================== TEST FIND BY VAI TRÒ =====================
        System.out.println("\n--- TÌM THEO VAI TRÒ ---");
        List<NguoiDung> listByRole = dao.findByVaiTro("STAFF");
        for (NguoiDung u : listByRole) {
            System.out.println(
                    u.getMaNguoiDung() + " | " +
                            u.getTenNguoiDung()
            );
        }

        // ===================== TEST FIND BY ID =====================
        if (!list.isEmpty()) {
            int id = list.get(0).getMaNguoiDung();
            NguoiDung found = dao.findById(id);

            System.out.println("\n--- FIND BY ID ---");
            System.out.println(found.getTenNguoiDung());
        }

        // ===================== TEST UPDATE =====================
        if (!listByTen.isEmpty()) {
            NguoiDung updateUser = listByTen.get(0);

            updateUser.setTenNguoiDung("User Updated");
            updateUser.setVaiTro("ADMIN");

            dao.update(updateUser);
            System.out.println("\nCập nhật thành công!");
        }

        // ===================== TEST DELETE =====================
        List<NguoiDung> deleteList = dao.findByTen("User Updated");
        if (!deleteList.isEmpty()) {
            int idDelete = deleteList.get(0).getMaNguoiDung();

            dao.delete(idDelete);
            System.out.println("\nXóa thành công!");
        } else {
            System.out.println("\nKhông có dữ liệu để xóa!");
        }
    }
}