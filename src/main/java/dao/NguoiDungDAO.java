package dao;

import entity.NguoiDung;
import java.util.List;

public interface NguoiDungDAO {

    // CRUD
    void insert(NguoiDung nd);
    void update(NguoiDung nd);
    void delete(int maNguoiDung);
    NguoiDung findById(int maNguoiDung);
    List<NguoiDung> findAll();
    NguoiDung checkLogin(String username, String password);

    // Tìm kiếm
    List<NguoiDung> findByTen(String ten);
    List<NguoiDung> findByTrangThai(boolean trangThai);
    List<NguoiDung> findByVaiTro(String vaiTro);
    NguoiDung login(String username, String password);
    NguoiDung findByTenDangNhap(String tenDangNhap);
}

