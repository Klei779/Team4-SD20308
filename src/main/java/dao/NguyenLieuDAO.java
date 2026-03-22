package dao;

import entity.NguyenLieu;
import java.util.List;

public interface NguyenLieuDAO {

    void insert(NguyenLieu nl);
    void update(NguyenLieu nl);
    void delete(int id);
    void updateSoLuong(int maNguyenLieu, int soLuongThayDoi);

    List<NguyenLieu> findAll();

    List<NguyenLieu> findByTen(String ten);

    List<NguyenLieu> findByLoai(int maLoaiNguyenLieu);

    NguyenLieu findById(int id);

}