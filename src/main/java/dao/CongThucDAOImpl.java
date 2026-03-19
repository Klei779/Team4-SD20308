package dao;

import entity.CongThuc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import util.JDBC;

public class CongThucDAOImpl implements CongThucDAO{
    @Override
    public void insert(CongThuc ct) {
        String sql = "INSERT INTO CongThuc(tenCongThuc) VALUES(?)";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ct.getTenCongThuc());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(CongThuc ct) {
        String sql = "UPDATE CongThuc SET tenCongThuc = ? WHERE maCongThuc = ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ct.getTenCongThuc());
            ps.setInt(2, ct.getMaCongThuc());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(CongThuc ct) {
        String sql1 = "DELETE FROM CongThucChiTiet WHERE maCongThuc = ?";
        String sql2 = "DELETE FROM DoUong WHERE maCongThuc = ?";
        String sql3 = "DELETE FROM CongThuc WHERE maCongThuc = ?";

        try (Connection conn = JDBC.getConnection()) {

            conn.setAutoCommit(false); // bật transaction

            try (
                    PreparedStatement ps1 = conn.prepareStatement(sql1);
                    PreparedStatement ps2 = conn.prepareStatement(sql2);
                    PreparedStatement ps3 = conn.prepareStatement(sql3)
            ) {
                int id = ct.getMaCongThuc();

                ps1.setInt(1, id);
                ps1.executeUpdate();

                ps2.setInt(1, id);
                ps2.executeUpdate();

                ps3.setInt(1, id);
                ps3.executeUpdate();

                conn.commit();

            } catch (Exception e) {
                conn.rollback();
                e.printStackTrace();
            }

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
                CongThuc ct = new CongThuc();
                ct.setMaCongThuc(rs.getInt("maCongThuc"));
                ct.setTenCongThuc(rs.getString("tenCongThuc"));

                list.add(ct);
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
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CongThuc ct = new CongThuc();
                ct.setMaCongThuc(rs.getInt("maCongThuc"));
                ct.setTenCongThuc(rs.getString("tenCongThuc"));

                list.add(ct);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    }
