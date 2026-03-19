package dao;

import entity.CongThucChiTiet;
import util.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CongThucCTDAOImpl implements CongThucCTDAO {


        @Override
        public void insert(CongThucChiTiet ctct) {
            String sql = "INSERT INTO CongThucChiTiet(maCongThuc, maNguyenLieu, dinhLuong) VALUES (?, ?, ?)";

            try (Connection conn = JDBC.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, ctct.getMaCongThuc());
                ps.setInt(2, ctct.getMaNguyenLieu());
                ps.setInt(3, ctct.getDinhLuong());

                ps.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void update(CongThucChiTiet ctct) {
            String sql = "UPDATE CongThucChiTiet SET maCongThuc = ?, maNguyenLieu = ?, dinhLuong = ? WHERE maCongThucCT = ?";

            try (Connection conn = JDBC.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, ctct.getMaCongThuc());
                ps.setInt(2, ctct.getMaNguyenLieu());
                ps.setInt(3, ctct.getDinhLuong());
                ps.setInt(4, ctct.getMaCTCT());

                ps.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void delete(int id) {
            String sql = "DELETE FROM CongThucChiTiet WHERE maCongThucCT = ?";

            try (Connection conn = JDBC.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, id);
                ps.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public List<CongThucChiTiet> findAll() {
            List<CongThucChiTiet> list = new ArrayList<>();
            String sql = "SELECT * FROM CongThucChiTiet";

            try (Connection conn = JDBC.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    CongThucChiTiet ctct = new CongThucChiTiet();
                    ctct.setMaCTCT(rs.getInt("maCongThucCT"));
                    ctct.setMaCongThuc(rs.getInt("maCongThuc"));
                    ctct.setMaNguyenLieu(rs.getInt("maNguyenLieu"));
                    ctct.setDinhLuong(rs.getInt("dinhLuong"));

                    list.add(ctct);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return list;
        }

        @Override
        public List<CongThucChiTiet> findByCongThuc(int maCongThuc) {
            List<CongThucChiTiet> list = new ArrayList<>();
            String sql = "SELECT * FROM CongThucChiTiet WHERE maCongThuc = ?";

            try (Connection conn = JDBC.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, maCongThuc);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    CongThucChiTiet ctct = new CongThucChiTiet();
                    ctct.setMaCTCT(rs.getInt("maCongThucCT"));
                    ctct.setMaCongThuc(rs.getInt("maCongThuc"));
                    ctct.setMaNguyenLieu(rs.getInt("maNguyenLieu"));
                    ctct.setDinhLuong(rs.getInt("dinhLuong"));

                    list.add(ctct);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return list;
        }
    }

