package dao;

import entity.DoUong;
import util.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoUongDAOImpl implements DoUongDAO {

    @Override
    public void insert(DoUong d) {
        String sql = "INSERT INTO DoUong(maLoai, maCongThuc, tenDoUong, giaTien, giaVon, moTa, hinhAnh, khuyenMai, trangThai) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, d.getMaLoai());
            ps.setInt(2, d.getMaCongThuc());
            ps.setString(3, d.getTenDoUong());
            ps.setInt(4, d.getGiaTien());
            ps.setBigDecimal(5, d.getGiaVon());
            ps.setString(6, d.getMoTa());
            ps.setString(7, d.getHinhAnh());
            ps.setBigDecimal(8, d.getKhuyenMai());
            ps.setBoolean(9, d.isTrangThai());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(DoUong d) {
        String sql = "UPDATE DoUong SET maLoai=?, maCongThuc=?, tenDoUong=?, giaTien=?, giaVon=?, moTa=?, hinhAnh=?, khuyenMai=?, trangThai=? WHERE maDoUong=?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, d.getMaLoai());
            ps.setInt(2, d.getMaCongThuc());
            ps.setString(3, d.getTenDoUong());
            ps.setInt(4, d.getGiaTien());
            ps.setBigDecimal(5, d.getGiaVon());
            ps.setString(6, d.getMoTa());
            ps.setString(7, d.getHinhAnh());
            ps.setBigDecimal(8, d.getKhuyenMai());
            ps.setBoolean(9, d.isTrangThai());
            ps.setInt(10, d.getMaDoUong());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int maDoUong) {
        String sql = "DELETE FROM DoUong WHERE maDoUong = ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maDoUong);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<DoUong> findAll() {
        List<DoUong> list = new ArrayList<>();
        String sql = "SELECT * FROM DoUong";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                DoUong d = new DoUong();

                d.setMaDoUong(rs.getInt("maDoUong"));
                d.setMaLoai(rs.getInt("maLoai"));
                d.setMaCongThuc(rs.getInt("maCongThuc"));
                d.setTenDoUong(rs.getString("tenDoUong"));
                d.setGiaTien(rs.getInt("giaTien"));
                d.setGiaVon(rs.getBigDecimal("giaVon"));
                d.setMoTa(rs.getString("moTa"));
                d.setHinhAnh(rs.getString("hinhAnh"));
                d.setKhuyenMai(rs.getBigDecimal("khuyenMai"));
                d.setTrangThai(rs.getBoolean("trangThai"));
                list.add(d);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<DoUong> findByTrangThai(boolean trangThai) {
        List<DoUong> list = new ArrayList<>();

        String sql = "SELECT * FROM DoUong WHERE trangThai = ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // 1. Gán tham số
            ps.setBoolean(1, trangThai);

            // 2. Thực thi query
            ResultSet rs = ps.executeQuery();

            // 3. Duyệt từng dòng
            while (rs.next()) {

                DoUong d = new DoUong();

                d.setMaDoUong(rs.getInt("maDoUong"));
                d.setMaLoai(rs.getInt("maLoai"));
                d.setMaCongThuc(rs.getInt("maCongThuc"));
                d.setTenDoUong(rs.getString("tenDoUong"));

                // nếu bạn dùng int
                d.setGiaTien(rs.getInt("giaTien"));
                d.setGiaVon(rs.getBigDecimal("giaVon"));

                d.setMoTa(rs.getString("moTa"));
                d.setHinhAnh(rs.getString("hinhAnh"));
                d.setKhuyenMai(rs.getBigDecimal("khuyenMai"));
                d.setTrangThai(rs.getBoolean("trangThai"));

                // 4. Thêm vào list
                list.add(d);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 5. Trả về kết quả
        return list;
    }
    @Override
    public List<DoUong> findByGiaTien(int giaTien) {
        List<DoUong> list = new ArrayList<>();

        String sql = "SELECT * FROM DoUong WHERE giaTien = ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // 1. Gán tham số
            ps.setInt(1,giaTien);

            // 2. Thực thi query
            ResultSet rs = ps.executeQuery();

            // 3. Duyệt từng dòng
            while (rs.next()) {

                DoUong d = new DoUong();

                d.setMaDoUong(rs.getInt("maDoUong"));
                d.setMaLoai(rs.getInt("maLoai"));
                d.setMaCongThuc(rs.getInt("maCongThuc"));
                d.setTenDoUong(rs.getString("tenDoUong"));

                // nếu bạn dùng int
                d.setGiaTien(rs.getInt("giaTien"));
                d.setGiaVon(rs.getBigDecimal("giaVon"));

                d.setMoTa(rs.getString("moTa"));
                d.setHinhAnh(rs.getString("hinhAnh"));
                d.setKhuyenMai(rs.getBigDecimal("khuyenMai"));
                d.setTrangThai(rs.getBoolean("trangThai"));

                list.add(d);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 5. Trả về kết quả
        return list;
    }
    @Override
    public List<DoUong> findByTenDoUong(String tenDoUong) {
        List<DoUong> list = new ArrayList<>();

        String sql = "SELECT * FROM DoUong WHERE tenDoUong LIKE ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + tenDoUong + "%");

            // 2. Thực thi query
            ResultSet rs = ps.executeQuery();

            // 3. Duyệt từng dòng
            while (rs.next()) {

                DoUong d = new DoUong();

                d.setMaDoUong(rs.getInt("maDoUong"));
                d.setMaLoai(rs.getInt("maLoai"));
                d.setMaCongThuc(rs.getInt("maCongThuc"));
                d.setTenDoUong(rs.getString("tenDoUong"));

                d.setGiaTien(rs.getInt("giaTien"));
                d.setGiaVon(rs.getBigDecimal("giaVon"));

                d.setMoTa(rs.getString("moTa"));
                d.setHinhAnh(rs.getString("hinhAnh"));
                d.setKhuyenMai(rs.getBigDecimal("khuyenMai"));
                d.setTrangThai(rs.getBoolean("trangThai"));
                // 4. Thêm vào list
                list.add(d);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 5. Trả về kết quả
        return list;
    }
    @Override
    public List<DoUong> findByMaLoai(int maLoaiDoUong) {
        List<DoUong> list = new ArrayList<>();

        String sql = "SELECT * FROM DoUong WHERE maLoai = ? AND trangThai = 1";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // 1. Gán tham số
            ps.setInt(1,maLoaiDoUong);

            // 2. Thực thi query
            ResultSet rs = ps.executeQuery();

            // 3. Duyệt từng dòng
            while (rs.next()) {

                DoUong d = new DoUong();

                d.setMaDoUong(rs.getInt("maDoUong"));
                d.setMaLoai(rs.getInt("maLoai"));
                d.setMaCongThuc(rs.getInt("maCongThuc"));
                d.setTenDoUong(rs.getString("tenDoUong"));

                // nếu bạn dùng int
                d.setGiaTien(rs.getInt("giaTien"));
                d.setGiaVon(rs.getBigDecimal("giaVon"));

                d.setMoTa(rs.getString("moTa"));
                d.setHinhAnh(rs.getString("hinhAnh"));
                d.setKhuyenMai(rs.getBigDecimal("khuyenMai"));
                d.setTrangThai(rs.getBoolean("trangThai"));
                // 4. Thêm vào list
                list.add(d);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 5. Trả về kết quả
        return list;
    }

    @Override
    public DoUong findById(int id) {
        String sql = "SELECT * FROM DoUong WHERE maDoUong = ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            DoUong d = null;

            if (rs.next()) {
                d = new DoUong();

                d.setMaDoUong(rs.getInt("maDoUong"));
                d.setMaLoai(rs.getInt("maLoai"));
                d.setMaCongThuc(rs.getInt("maCongThuc"));
                d.setTenDoUong(rs.getString("tenDoUong"));
                d.setGiaTien(rs.getInt("giaTien"));
                d.setGiaVon(rs.getBigDecimal("giaVon"));
                d.setMoTa(rs.getString("moTa"));
                d.setHinhAnh(rs.getString("hinhAnh"));
                d.setKhuyenMai(rs.getBigDecimal("khuyenMai"));
                d.setTrangThai(rs.getBoolean("trangThai"));
            }

            return d;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}