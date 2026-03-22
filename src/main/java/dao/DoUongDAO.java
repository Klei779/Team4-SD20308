package dao;

import entity.DoUong;

import java.util.List;

public interface DoUongDAO {
    void insert(DoUong d);
    void update(DoUong d);
    void delete(int maDoUong);
    DoUong findById(int id);
    List<DoUong> findAll();
    List<DoUong> findByMaLoai(int maLoaiDoUong);
    List<DoUong> findByTenDoUong(String tenDoUong);
    List<DoUong> findByGiaTien(int giaTien);
    List<DoUong> findByTrangThai(boolean trangThai);

}
