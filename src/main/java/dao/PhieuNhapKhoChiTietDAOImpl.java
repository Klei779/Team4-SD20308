package dao;

import entity.PhieuNhapKhoChiTiet;
import util.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhieuNhapKhoChiTietDAOImpl implements PhieuNhapKhoChiTietDAO {

    private PhieuNhapKhoChiTiet map(ResultSet rs) throws Exception {

        PhieuNhapKhoChiTiet ct = new PhieuNhapKhoChiTiet();

        ct.setMaPhieuNhapKhoCT(rs.getInt("maPhieuNKCT")); // FIX
        ct.setMaPhieuNhapKho(rs.getInt("maPhieuNhapKho"));
        ct.setMaNguyenLieu(rs.getInt("maNguyenLieu"));
        ct.setSoLuong(rs.getInt("soLuong"));
        ct.setNgayHetHan(rs.getDate("ngayHetHan"));
        ct.setDonGiaNhap(rs.getInt("donGiaNhap")); // FIX

        return ct;
    }

    // ===== INSERT =====
    @Override
    public void insert(PhieuNhapKhoChiTiet ct) {

        String sql =
                "INSERT INTO PhieuNhapKhoChiTiet " +
                        "(maPhieuNhapKho, maNguyenLieu, soLuong, ngayHetHan, donGiaNhap) " +
                        "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ct.getMaPhieuNhapKho());
            ps.setInt(2, ct.getMaNguyenLieu());
            ps.setInt(3, ct.getSoLuong());

            if (ct.getNgayHetHan() != null)
                ps.setDate(4, ct.getNgayHetHan());
            else
                ps.setNull(4, Types.DATE);

            ps.setInt(5, ct.getDonGiaNhap());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== FIND BY PHIẾU =====
    @Override
    public List<PhieuNhapKhoChiTiet> findByPhieuNhapKho(int maPhieuNhapKho) {

        List<PhieuNhapKhoChiTiet> list = new ArrayList<>();

        String sql = "SELECT * FROM PhieuNhapKhoChiTiet WHERE maPhieuNhapKho=?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maPhieuNhapKho);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ===== GET TÊN NL =====
    @Override
    public String getTenNguyenLieu(int ma) {

        String sql = "SELECT tenNguyenLieu FROM NguyenLieu WHERE maNguyenLieu=?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ma);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getString("tenNguyenLieu");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "N/A";
    }


    // ================= FIND ALL =================
    @Override
    public List<PhieuNhapKhoChiTiet> findAll() {

        List<PhieuNhapKhoChiTiet> list = new ArrayList<>();

        String sql = "SELECT * FROM PhieuNhapKhoChiTiet";

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


}