package dao;

import entity.NgayDTO;
import entity.ThongKeDTO;
import entity.ThongKeDoUongDTO;
import entity.ThongKeNhanVienDTO;
import util.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ThongKeDAOImpl implements ThongKeDAO {

    @Override
    public ThongKeDTO getThongKe(Date fromDate, Date toDate) {

        String sql =
                "SELECT " +
                        "ISNULL(SUM(hd.tongTien),0) AS doanhThu, " +
                        "ISNULL(SUM(hdct.soLuong * (du.giaTien - du.giaVon)),0) AS loiNhuan, " +
                        "COUNT(DISTINCT hd.maHoaDon) AS soHoaDon, " +
                        "ISNULL(SUM(hdct.soLuong),0) AS tongSoLuong " +
                        "FROM HoaDon hd " +
                        "JOIN HoaDonChiTiet hdct ON hd.maHoaDon = hdct.maHoaDon " +
                        "JOIN DoUong du ON hdct.maDoUong = du.maDoUong " +
                        "WHERE hd.ngayTao BETWEEN ? AND ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, new Timestamp(fromDate.getTime()));
            ps.setTimestamp(2, new Timestamp(toDate.getTime()));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ThongKeDTO tk = new ThongKeDTO();
                    tk.setDoanhThu(rs.getInt("doanhThu"));
                    tk.setLoiNhuan(rs.getInt("loiNhuan"));
                    tk.setSoHoaDon(rs.getInt("soHoaDon"));
                    tk.setTongSoLuong(rs.getInt("tongSoLuong"));
                    return tk;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<ThongKeDoUongDTO> getTopDoUong(Date fromDate, Date toDate) {
        List<ThongKeDoUongDTO> list = new ArrayList<>();

        String sql =
                "SELECT " +
                        "du.tenDoUong, " +
                        "ISNULL(SUM(hdct.soLuong),0) AS soLuong, " +
                        "ISNULL(SUM(hdct.soLuong * hdct.donGia),0) AS doanhThu " +
                        "FROM HoaDonChiTiet hdct " +
                        "JOIN HoaDon hd ON hd.maHoaDon = hdct.maHoaDon " +
                        "JOIN DoUong du ON du.maDoUong = hdct.maDoUong " +
                        "WHERE hd.ngayTao BETWEEN ? AND ? " +
                        "GROUP BY du.tenDoUong " +
                        "ORDER BY soLuong DESC";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, new Timestamp(fromDate.getTime()));
            ps.setTimestamp(2, new Timestamp(toDate.getTime()));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ThongKeDoUongDTO dto = new ThongKeDoUongDTO();dto.setTenDoUong(rs.getString("tenDoUong"));
                    dto.setSoLuong(rs.getInt("soLuong"));
                    dto.setDoanhThu(rs.getInt("doanhThu"));
                    list.add(dto);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<ThongKeNhanVienDTO> getDoanhThuNhanVien(Date fromDate, Date toDate) {
        List<ThongKeNhanVienDTO> list = new ArrayList<>();

        String sql =
                "SELECT " +
                        "nd.tenNguoiDung, " +
                        "ISNULL(SUM(hd.tongTien),0) AS doanhThu " +
                        "FROM HoaDon hd " +
                        "JOIN NguoiDung nd ON hd.maNguoiDung = nd.maNguoiDung " +
                        "WHERE hd.ngayTao BETWEEN ? AND ? " +
                        "GROUP BY nd.tenNguoiDung " +
                        "ORDER BY doanhThu DESC";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, new Timestamp(fromDate.getTime()));
            ps.setTimestamp(2, new Timestamp(toDate.getTime()));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ThongKeNhanVienDTO dto = new ThongKeNhanVienDTO();
                    dto.setTenNhanVien(rs.getString("tenNguoiDung"));
                    dto.setDoanhThu(rs.getInt("doanhThu"));
                    list.add(dto);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    @Override
    public List<NgayDTO> getDoanhThuTheoNgay(Date from, Date to) {
        List<NgayDTO> list = new ArrayList<>();

        String sql =
                "SELECT CONVERT(date, ngayTao) as ngay, " +
                        "SUM(tongTien) as doanhThu " +
                        "FROM HoaDon " +
                        "WHERE ngayTao BETWEEN ? AND ? " +
                        "GROUP BY CONVERT(date, ngayTao) " +
                        "ORDER BY ngay";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, new Timestamp(from.getTime()));
            ps.setTimestamp(2, new Timestamp(to.getTime()));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                NgayDTO n = new NgayDTO();
                n.setNgay(rs.getDate("ngay"));
                n.setDoanhThu(rs.getInt("doanhThu"));
                list.add(n);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}