package dao;

import entity.CongThuc;
import util.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CongThucDAOImpl implements CongThucDAO {

    @Override
    public void insert(CongThuc ct) {
        String sql = "INSERT INTO CongThuc (tenCongThuc, trangThai) VALUES (?, ?)";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setNString(1, ct.getTenCongThuc());
            ps.setBoolean(2, ct.isTrangThai());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(CongThuc ct) {
        String sql = "UPDATE CongThuc SET tenCongThuc = ?, trangThai = ? WHERE maCongThuc = ?";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setNString(1, ct.getTenCongThuc());
            ps.setBoolean(2, ct.isTrangThai());
            ps.setInt(3, ct.getMaCongThuc());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void softDelete(int maCongThuc) {
        // Cập nhật trạng thái thành 0 (Ẩn) thay vì xóa khỏi DB
        String sql = "UPDATE CongThuc SET trangThai = 0 WHERE maCongThuc = ?";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maCongThuc);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resetData(int maCongThuc) {
        // Xóa vĩnh viễn bản ghi (Lưu ý: Sẽ lỗi nếu có Đồ uống đang dùng công thức này)
        String sql = "DELETE FROM CongThuc WHERE maCongThuc = ?";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maCongThuc);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Lỗi: Không thể xóa vì công thức đang được sử dụng ở bảng khác.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<CongThuc> findall() {
        List<CongThuc> list = new ArrayList<>();
        String sql = "SELECT * FROM CongThuc";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new CongThuc(
                        rs.getInt("maCongThuc"),
                        rs.getNString("tenCongThuc"),
                        rs.getBoolean("trangThai")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<CongThuc> findActive() {
        List<CongThuc> list = new ArrayList<>();
        String sql = "SELECT * FROM CongThuc WHERE trangThai = 1";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new CongThuc(
                        rs.getInt("maCongThuc"),
                        rs.getNString("tenCongThuc"),
                        rs.getBoolean("trangThai")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<CongThuc> findByCongThuc(int maCongThuc) {
        List<CongThuc> list = new ArrayList<>();
        String sql = "SELECT * FROM CongThuc WHERE maCongThuc = ?";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maCongThuc);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new CongThuc(
                            rs.getInt("maCongThuc"),
                            rs.getNString("tenCongThuc"),
                            rs.getBoolean("trangThai")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}