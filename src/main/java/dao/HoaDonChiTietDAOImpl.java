package dao;

import entity.HoaDonChiTiet;
import util.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HoaDonChiTietDAOImpl implements HoaDonChiTietDAO {

    // ================= MAP RESULT =================
    private HoaDonChiTiet mapResultSet(ResultSet rs) throws SQLException {
        HoaDonChiTiet hdct = new HoaDonChiTiet();
        hdct.setMaHDCT(rs.getInt("maHDCT"));
        hdct.setMaHoaDon(rs.getInt("maHoaDon"));
        hdct.setMaDoUong(rs.getInt("maDoUong"));
        hdct.setSoLuong(rs.getInt("soLuong"));
        hdct.setDonGia(rs.getInt("donGia"));
        return hdct;
    }

    // ================= SELECT ALL =================
    @Override
    public List<HoaDonChiTiet> selectAll() {
        List<HoaDonChiTiet> list = new ArrayList<>();
        String sql = "SELECT * FROM HoaDonChiTiet";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

        } catch (Exception e) {
            System.out.println("Lỗi selectAll HDCT: " + e.getMessage());
        }

        return list;
    }

    // ================= SELECT BY BILL ID =================
    @Override
    public List<HoaDonChiTiet> selectByHoaDonId(int maHoaDon) {
        List<HoaDonChiTiet> list = new ArrayList<>();
        String sql = "SELECT * FROM HoaDonChiTiet WHERE maHoaDon = ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maHoaDon);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

        } catch (Exception e) {
            System.out.println("Lỗi selectByHoaDonId: " + e.getMessage());
        }

        return list;
    }

    // ================= LẤY TÊN ĐỒ UỐNG =================
    @Override
    public String getTenDoUong(int maDoUong) {
        String sql = "SELECT tenDoUong FROM DoUong WHERE maDoUong = ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maDoUong);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("tenDoUong");
            }

        } catch (Exception e) {
            System.out.println("Lỗi getTenDoUong: " + e.getMessage());
        }

        return null;
    }
}