package dao;

import entity.CongThucChiTiet;
import java.util.List;

public interface CongThucCTDAO {
    void insert(CongThucChiTiet ctct);
    void update(CongThucChiTiet ctct);
    void delete(int id);
    List<CongThucChiTiet> findAll();
    List<CongThucChiTiet> findByCongThuc(int maCongThuc);
    boolean existsByNguyenLieu(int maNguyenLieu);
}
