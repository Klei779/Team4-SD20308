package dao;

import entity.HoaDonChiTiet;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface HoaDonChiTietDAO {

    void insert(HoaDonChiTiet ct, Connection conn);

    List<HoaDonChiTiet> selectAll();

    List<HoaDonChiTiet> selectByHoaDonId(int maHoaDon) throws Exception;

    String getTenDoUong(int maDoUong);

    List<Map<String, Object>> selectChiTietWithProduct(int maHoaDon);
}
