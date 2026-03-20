package dao;

import entity.HoaDon;
import java.util.List;
import java.sql.Timestamp;

public interface HoaDonDAO {

    List<HoaDon> selectAll();

    List<HoaDon> selectByMaNguoiDung(int maNguoiDung);

    List<HoaDon> selectByTrangThai(boolean trangThai);

    List<HoaDon> selectByDate(Timestamp from, Timestamp to);

    String getTenNhanVien(int maNguoiDung);
}
