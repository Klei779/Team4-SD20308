package dao;

import entity.GioHang;
import entity.HoaDon;

import java.sql.Connection;
import java.util.List;
import java.sql.Timestamp;

public interface HoaDonDAO {
    int insertReturnId(HoaDon hd, Connection conn);

    HoaDon selectById(int maHoaDon);

    List<HoaDon> selectAll();

    List<HoaDon> selectByMaNguoiDung(int maNguoiDung);

    List<HoaDon> selectByTrangThai(boolean trangThai);

    List<HoaDon> selectByDate(Timestamp from, Timestamp to);

    String getTenNhanVien(int maNguoiDung);

    int insertHoaDon(int maNguoiDung, double tongTien);

    void insertHoaDonChiTiet(int maHD, GioHang g);
}
