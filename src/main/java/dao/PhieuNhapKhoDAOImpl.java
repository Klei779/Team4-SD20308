package dao;

import entity.PhieuNhapKho;
import util.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PhieuNhapKhoDAOImpl implements PhieuNhapKhoDAO {

    private PhieuNhapKho map(ResultSet rs) throws Exception {
        PhieuNhapKho pnk = new PhieuNhapKho();
        pnk.setMaPhieuNhapKho(rs.getInt("maPhieuNhapKho"));
        pnk.setMaNguoiDung(rs.getInt("maNguoiDung"));
        pnk.setMaNCC(rs.getInt("maNCC"));
        pnk.setNgayNhapKho(rs.getTimestamp("ngayNhapKho"));
        pnk.setTongTien(rs.getInt("tongTien"));
        pnk.setGhiChu(rs.getString("ghiChu"));
        return pnk;
    }

    @Override
    public void insert(PhieuNhapKho pnk) {

        String sql =
                "INSERT INTO PhieuNhapKho " +
                        "(maNguoiDung, maNCC, ngayNhapKho, tongTien, ghiChu) " +
                        "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, pnk.getMaNguoiDung());
            ps.setInt(2, pnk.getMaNCC());
            ps.setTimestamp(3, pnk.getNgayNhapKho());
            ps.setInt(4, pnk.getTongTien());
            ps.setString(5, pnk.getGhiChu());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<PhieuNhapKho> findAll() {
        List<PhieuNhapKho> list = new ArrayList<>();

        String sql = "SELECT * FROM PhieuNhapKho ORDER BY ngayNhapKho DESC";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<PhieuNhapKho> findByDate(Date from, Date to) {
        List<PhieuNhapKho> list = new ArrayList<>();

        String sql =
                "SELECT * FROM PhieuNhapKho " +
                        "WHERE ngayNhapKho BETWEEN ? AND ? " +
                        "ORDER BY ngayNhapKho DESC";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, new Timestamp(from.getTime()));
            ps.setTimestamp(2, new Timestamp(to.getTime()));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<PhieuNhapKho> findByNguoiDung(int maNguoiDung) {
        List<PhieuNhapKho> list = new ArrayList<>();

        String sql =
                "SELECT * FROM PhieuNhapKho " +
                        "WHERE maNguoiDung = ? " +
                        "ORDER BY ngayNhapKho DESC";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maNguoiDung);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<PhieuNhapKho> findByNCC(int maNCC) {
        List<PhieuNhapKho> list = new ArrayList<>();

        String sql =
                "SELECT * FROM PhieuNhapKho " +
                        "WHERE maNCC = ? " +
                        "ORDER BY ngayNhapKho DESC";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maNCC);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}