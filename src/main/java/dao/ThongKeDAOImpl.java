package dao;

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
        String sql = """
            SELECT 
                SUM(hd.tongTien) AS doanhThu,
                SUM(hdct.soLuong * (du.giaTien - du.giaVon)) AS loiNhuan,
                COUNT(DISTINCT hd.maHoaDon) AS soHoaDon,
                SUM(hdct.soLuong) AS tongSoLuong
            FROM HoaDon hd
            JOIN HoaDonChiTiet hdct ON hd.maHoaDon = hdct.maHoaDon
            JOIN DoUong du ON hdct.maDoUong = du.maDoUong
            WHERE hd.ngayTao BETWEEN ? AND ?
        """;

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, new Timestamp(fromDate.getTime()));
            ps.setTimestamp(2, new Timestamp(toDate.getTime()));

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ThongKeDTO tk = new ThongKeDTO();
                tk.setDoanhThu(rs.getDouble("doanhThu"));
                tk.setLoiNhuan(rs.getDouble("loiNhuan"));
                tk.setSoHoaDon(rs.getInt("soHoaDon"));
                tk.setTongSoLuong(rs.getInt("tongSoLuong"));
                return tk;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ==========================
    // 🍹 Top đồ uống
    // ==========================
    @Override
    public List<ThongKeDoUongDTO> getTopDoUong(Date fromDate, Date toDate) {
        List<ThongKeDoUongDTO> list = new ArrayList<>();

        String sql = """
            SELECT 
                du.tenDoUong,
                SUM(hdct.soLuong) AS soLuong,
                SUM(hdct.soLuong * hdct.donGia) AS doanhThu
            FROM HoaDonChiTiet hdct
            JOIN HoaDon hd ON hd.maHoaDon = hdct.maHoaDon
            JOIN DoUong du ON du.maDoUong = hdct.maDoUong
            WHERE hd.ngayTao BETWEEN ? AND ?
            GROUP BY du.tenDoUong
            ORDER BY soLuong DESC
        """;

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, new Timestamp(fromDate.getTime()));
            ps.setTimestamp(2, new Timestamp(toDate.getTime()));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ThongKeDoUongDTO dto = new ThongKeDoUongDTO();
                dto.setTenDoUong(rs.getString("tenDoUong"));
                dto.setSoLuong(rs.getInt("soLuong"));
                dto.setDoanhThu(rs.getDouble("doanhThu"));
                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ==========================
    // 👨‍💼 Theo nhân viên
    // ==========================
    @Override
    public List<ThongKeNhanVienDTO> getDoanhThuNhanVien(Date fromDate, Date toDate) {
        List<ThongKeNhanVienDTO> list = new ArrayList<>();

        String sql = """
            SELECT 
                nd.tenNguoiDung,
                SUM(hd.tongTien) AS doanhThu
            FROM HoaDon hd
            JOIN NguoiDung nd ON hd.maNguoiDung = nd.maNguoiDung
            WHERE hd.ngayTao BETWEEN ? AND ?
            GROUP BY nd.tenNguoiDung
            ORDER BY doanhThu DESC
        """;

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, new Timestamp(fromDate.getTime()));
            ps.setTimestamp(2, new Timestamp(toDate.getTime()));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ThongKeNhanVienDTO dto = new ThongKeNhanVienDTO();
                dto.setTenNhanVien(rs.getString("tenNguoiDung"));
                dto.setDoanhThu(rs.getDouble("doanhThu"));
                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}