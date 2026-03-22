package dao;

import entity.NguyenLieu;
import util.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NguyenLieuDAOImpl implements NguyenLieuDAO {

    private NguyenLieu map(ResultSet rs) throws Exception {
        NguyenLieu nl = new NguyenLieu();
        nl.setMaNguyenLieu(rs.getInt("maNguyenLieu"));
        nl.setMaLoaiNguyenLieu(rs.getInt("maLoaiNguyenLieu"));
        nl.setTenNguyenLieu(rs.getString("tenNguyenLieu"));
        nl.setSoLuongTon(rs.getInt("soLuongTon"));
        nl.setDonVi(rs.getString("donVi"));
        nl.setSoLuongToiThieu(rs.getInt("soLuongToiThieu"));
        nl.setGhiChu(rs.getString("ghiChu"));
        return nl;
    }

    @Override
    public void insert(NguyenLieu nl) {

        String sql =
                "INSERT INTO NguyenLieu " +
                        "(maLoaiNguyenLieu, tenNguyenLieu, soLuongTon, donVi, soLuongToiThieu, ghiChu) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, nl.getMaLoaiNguyenLieu());
            ps.setString(2, nl.getTenNguyenLieu());
            ps.setInt(3, nl.getSoLuongTon());
            ps.setString(4, nl.getDonVi());
            ps.setInt(5, nl.getSoLuongToiThieu());
            ps.setString(6, nl.getGhiChu() == null ? "" : nl.getGhiChu());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(NguyenLieu nl) {

        String sql =
                "UPDATE NguyenLieu SET " +
                        "maLoaiNguyenLieu = ?, " +
                        "tenNguyenLieu = ?, " +
                        "soLuongTon = ?, " +
                        "donVi = ?, " +
                        "soLuongToiThieu = ?, " +
                        "ghiChu = ? " +
                        "WHERE maNguyenLieu = ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, nl.getMaLoaiNguyenLieu());
            ps.setString(2, nl.getTenNguyenLieu());
            ps.setInt(3, nl.getSoLuongTon());
            ps.setString(4, nl.getDonVi());
            ps.setInt(5, nl.getSoLuongToiThieu());
            ps.setString(6, nl.getGhiChu());
            ps.setInt(7, nl.getMaNguyenLieu());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<NguyenLieu> findAll() {
        List<NguyenLieu> list = new ArrayList<>();

        String sql = "SELECT * FROM NguyenLieu ORDER BY tenNguyenLieu";

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
    public List<NguyenLieu> findByTen(String ten) {
        List<NguyenLieu> list = new ArrayList<>();

        String sql =
                "SELECT * FROM NguyenLieu " +
                        "WHERE tenNguyenLieu LIKE ? " +
                        "ORDER BY tenNguyenLieu";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + ten + "%");

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
    public List<NguyenLieu> findByLoai(int maLoaiNguyenLieu) {
        List<NguyenLieu> list = new ArrayList<>();

        String sql =
                "SELECT * FROM NguyenLieu " +
                        "WHERE maLoaiNguyenLieu = ? " +
                        "ORDER BY tenNguyenLieu";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maLoaiNguyenLieu);

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
    public void updateSoLuong(int maNguyenLieu, int soLuongThayDoi) {

        String sql = "UPDATE NguyenLieu SET soLuongTon = soLuongTon + ? WHERE maNguyenLieu = ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, soLuongThayDoi); // có thể + hoặc -
            ps.setInt(2, maNguyenLieu);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public NguyenLieu findById(int id) {

        String sql = "SELECT * FROM NguyenLieu WHERE maNguyenLieu = ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return map(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void delete(int id) {

        String sql = "DELETE FROM NguyenLieu WHERE maNguyenLieu = ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Không được phép xóa!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}