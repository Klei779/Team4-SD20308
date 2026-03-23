package dao;

import entity.HoaDonChiTiet;
import java.util.List;

public interface HoaDonChiTietDAO {

    void insert(HoaDonChiTiet ct) throws Exception;

    List<HoaDonChiTiet> selectAll();

    List<HoaDonChiTiet> selectByHoaDonId(int maHoaDon) throws Exception;

    String getTenDoUong(int maDoUong);
}
