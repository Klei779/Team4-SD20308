package dao;

import entity.HoaDon;
import util.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAOImpl implements HoaDonDAO {

    private HoaDon mapResultSet(ResultSet rs) throws SQLException {
        HoaDon hd = new HoaDon();
        hd.setMaHoaDon(rs.getInt("maHoaDon"));
        hd.setMaNguoiDung(rs.getInt("maNguoiDung"));
        hd.setNgayTao(rs.getTimestamp("ngayTao"));
        hd.setTrangThai(rs.getBoolean("trangThai"));
        hd.setTongTien(rs.getInt("tongTien"));
        return hd;
    }

    @Override
    public List<HoaDon> selectAll() {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

        } catch (Exception e) {
            System.out.println("Lỗi selectAll HoaDon: " + e.getMessage());
        }

        return list;
    }

    @Override
    public List<HoaDon> selectByMaNguoiDung(int maNguoiDung) {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon WHERE maNguoiDung = ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maNguoiDung);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

        } catch (Exception e) {
            System.out.println("Lỗi selectByMaNguoiDung: " + e.getMessage());
        }

        return list;
    }

    @Override
    public List<HoaDon> selectByTrangThai(boolean trangThai) {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon WHERE trangThai = ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBoolean(1, trangThai);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

        } catch (Exception e) {
            System.out.println("Lỗi selectByTrangThai: " + e.getMessage());
        }

        return list;
    }

    @Override
    public List<HoaDon> selectByDate(Timestamp from, Timestamp to) {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon WHERE ngayTao BETWEEN ? AND ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, from);
            ps.setTimestamp(2, to);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

        } catch (Exception e) {
            System.out.println("Lỗi selectByDate: " + e.getMessage());
        }

        return list;
    }

    @Override
    public String getTenNhanVien(int maNguoiDung) {
        String sql = "SELECT tenNguoiDung FROM NguoiDung WHERE maNguoiDung = ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maNguoiDung);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("tenNguoiDung");
            }

        } catch (Exception e) {
            System.out.println("Lỗi getTenNhanVien: " + e.getMessage());
        }

        return null;
    }
}