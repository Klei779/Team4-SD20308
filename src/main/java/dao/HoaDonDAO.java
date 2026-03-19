package dao;

import entity.HoaDon;
import java.util.List;
import java.sql.Timestamp;

public interface HoaDonDAO {

    // Lấy tất cả
    List<HoaDon> selectAll();

    // Theo mã nhân viên
    List<HoaDon> selectByMaNguoiDung(int maNguoiDung);

    // Theo trạng thái
    List<HoaDon> selectByTrangThai(boolean trangThai);

    // Theo ngày
    List<HoaDon> selectByDate(Timestamp from, Timestamp to);

    // Lấy tên nhân viên từ mã
    String getTenNhanVien(int maNguoiDung);
}
