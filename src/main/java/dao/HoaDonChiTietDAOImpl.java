package dao;

import entity.HoaDonChiTiet;
import util.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HoaDonChiTietDAOImpl implements HoaDonChiTietDAO {

    private HoaDonChiTiet mapResultSet(ResultSet rs) throws SQLException {
        HoaDonChiTiet hdct = new HoaDonChiTiet();
        hdct.setMaHDCT(rs.getInt("maHDCT"));
        hdct.setMaHoaDon(rs.getInt("maHoaDon"));
        hdct.setMaDoUong(rs.getInt("maDoUong"));
        hdct.setSoLuong(rs.getInt("soLuong"));
        hdct.setDonGia(rs.getInt("donGia"));
        return hdct;
    }


    @Override
    public void insert(HoaDonChiTiet ct, Connection conn) {
        try {
            String sql = "INSERT INTO HoaDonChiTiet(maHoaDon, maDoUong, donGia, soLuong) VALUES (?,?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, ct.getMaHoaDon());
            ps.setInt(2, ct.getMaDoUong());
            ps.setDouble(3, ct.getDonGia());
            ps.setInt(4, ct.getSoLuong());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    @Override
    public List<HoaDonChiTiet> selectByHoaDonId(int maHoaDon) throws Exception {
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

    // Trong HoaDonChiTietDAOImpl.java
    public List<Map<String, Object>> selectChiTietWithProduct(int maHoaDon) {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT ct.*, du.tenDoUong, du.hinhAnh " +
                "FROM HoaDonChiTiet ct " +
                "JOIN DoUong du ON ct.maDoUong = du.maDoUong " +
                "WHERE ct.maHoaDon = ?";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maHoaDon);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("tenDoUong", rs.getString("tenDoUong"));
                map.put("hinhAnh", rs.getString("hinhAnh"));
                map.put("soLuong", rs.getInt("soLuong"));
                map.put("donGia", rs.getInt("donGia"));
                map.put("thanhTien", rs.getInt("soLuong") * rs.getInt("donGia"));
                list.add(map);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}