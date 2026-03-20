package dao;

import entity.LoaiDoUong;
import util.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoaiDoUongDAOImpl implements LoaiDoUongDAO {

    private LoaiDoUong mapResultSet(ResultSet rs) throws SQLException {
        return new LoaiDoUong(
                rs.getInt("maLoai"),
                rs.getString("tenLoai")
        );
    }

    @Override
    public boolean insert(LoaiDoUong ldu) {
        String sql = "INSERT INTO LoaiDoUong(tenLoai) VALUES(?)";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ldu.getTenLoai());
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Lỗi insert LoaiDoUong: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(LoaiDoUong ldu) {
        String sql = "UPDATE LoaiDoUong SET tenLoai=? WHERE maLoai=?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ldu.getTenLoai());
            ps.setInt(2, ldu.getMaLoai());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Lỗi update LoaiDoUong: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int maLoai) {
        String sql = "DELETE FROM LoaiDoUong WHERE maLoai=?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maLoai);
            return ps.executeUpdate() > 0;

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Không thể xóa vì đang được sử dụng!");
        } catch (Exception e) {
            System.out.println("Lỗi delete LoaiDoUong: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<LoaiDoUong> selectAll() {
        List<LoaiDoUong> list = new ArrayList<>();
        String sql = "SELECT * FROM LoaiDoUong";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

        } catch (Exception e) {
            System.out.println("Lỗi selectAll LoaiDoUong: " + e.getMessage());
        }

        return list;
    }

    @Override
    public List<LoaiDoUong> selectByName(String tenLoai) {
        List<LoaiDoUong> list = new ArrayList<>();
        String sql = "SELECT * FROM LoaiDoUong WHERE tenLoai LIKE ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + (tenLoai == null ? "" : tenLoai) + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

        } catch (Exception e) {
            System.out.println("Lỗi selectByName LoaiDoUong: " + e.getMessage());
        }

        return list;
    }

    @Override
    public LoaiDoUong selectById(int maLoai) {
        String sql = "SELECT * FROM LoaiDoUong WHERE maLoai=?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maLoai);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSet(rs);
            }

        } catch (Exception e) {
            System.out.println("Lỗi selectById LoaiDoUong: " + e.getMessage());
        }

        return null;
    }
}