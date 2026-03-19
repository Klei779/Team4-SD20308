package dao;

import entity.HoaDonChiTiet;
import java.util.List;

public interface HoaDonChiTietDAO {

    // Lấy tất cả
    List<HoaDonChiTiet> selectAll();

    // Lấy theo mã hóa đơn
    List<HoaDonChiTiet> selectByHoaDonId(int maHoaDon);

    // Lấy tên đồ uống từ mã
    String getTenDoUong(int maDoUong);
}
