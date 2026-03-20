package dao;

import entity.HoaDonChiTiet;
import java.util.List;

public interface HoaDonChiTietDAO {

    List<HoaDonChiTiet> selectAll();

    List<HoaDonChiTiet> selectByHoaDonId(int maHoaDon);

    String getTenDoUong(int maDoUong);
}
