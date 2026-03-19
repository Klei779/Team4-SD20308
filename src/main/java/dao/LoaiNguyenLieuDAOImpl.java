package dao;

import entity.LoaiNguyenLieu;
import util.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LoaiNguyenLieuDAOImpl implements LoaiNguyenLieuDAO {

    @Override
    public void insert(LoaiNguyenLieu lnl) {
        String sql = "INSERT INTO LoaiNguyenLieu(tenLoaiNguyenLieu) VALUES (?)";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, lnl.getTenLoaiNguyenLieu());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(LoaiNguyenLieu lnl) {
        String sql = "UPDATE LoaiNguyenLieu SET tenLoaiNguyenLieu = ? WHERE maLoaiNguyenLieu = ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, lnl.getTenLoaiNguyenLieu());
            ps.setInt(2, lnl.getMaLoaiNguyenLieu());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(LoaiNguyenLieu lnl) {
        String sql = "DELETE FROM LoaiNguyenLieu WHERE maLoaiNguyenLieu = ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, lnl.getMaLoaiNguyenLieu());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<LoaiNguyenLieu> findall() {
        List<LoaiNguyenLieu> list = new ArrayList<>();
        String sql = "SELECT * FROM LoaiNguyenLieu";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                LoaiNguyenLieu lnl = new LoaiNguyenLieu();
                lnl.setMaLoaiNguyenLieu(rs.getInt("maLoaiNguyenLieu"));
                lnl.setTenLoaiNguyenLieu(rs.getString("tenLoaiNguyenLieu"));

                list.add(lnl);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<LoaiNguyenLieu> findByLoai(int maLoai) {
        List<LoaiNguyenLieu> list = new ArrayList<>();
        String sql = "SELECT * FROM LoaiNguyenLieu WHERE maLoaiNguyenLieu = ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maLoai);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LoaiNguyenLieu lnl = new LoaiNguyenLieu();
                lnl.setMaLoaiNguyenLieu(rs.getInt("maLoaiNguyenLieu"));
                lnl.setTenLoaiNguyenLieu(rs.getString("tenLoaiNguyenLieu"));

                list.add(lnl);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}