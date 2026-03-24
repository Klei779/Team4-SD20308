package dao;

import entity.GioHang;
import entity.HoaDon;
import util.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAOImpl implements HoaDonDAO {

    private HoaDon mapResultSet(ResultSet rs) throws SQLException {
        HoaDon hd = new HoaDon();
        hd.setMaHoaDon(rs.getInt("maHoaDon"));
        hd.setMaNguoiDung(rs.getInt("maNguoiDung"));
        hd.setNgayTao(rs.getTimestamp("ngayTao"));
        hd.setTrangThai(rs.getBoolean("trangThai"));
        hd.setTongTien(rs.getInt("tongTien"));
        return hd;
    }

    @Override
    public List<HoaDon> selectAll() {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

        } catch (Exception e) {
            System.out.println("Lỗi selectAll HoaDon: " + e.getMessage());
        }

        return list;
    }

    @Override
    public List<HoaDon> selectByMaNguoiDung(int maNguoiDung) {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon WHERE maNguoiDung = ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maNguoiDung);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

        } catch (Exception e) {
            System.out.println("Lỗi selectByMaNguoiDung: " + e.getMessage());
        }

        return list;
    }

    @Override
    public List<HoaDon> selectByTrangThai(boolean trangThai) {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon WHERE trangThai = ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBoolean(1, trangThai);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

        } catch (Exception e) {
            System.out.println("Lỗi selectByTrangThai: " + e.getMessage());
        }

        return list;
    }

    @Override
    public List<HoaDon> selectByDate(Timestamp from, Timestamp to) {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon WHERE ngayTao BETWEEN ? AND ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, from);
            ps.setTimestamp(2, to);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

        } catch (Exception e) {
            System.out.println("Lỗi selectByDate: " + e.getMessage());
        }

        return list;
    }

    @Override
    public String getTenNhanVien(int maNguoiDung) {
        String sql = "SELECT tenNguoiDung FROM NguoiDung WHERE maNguoiDung = ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maNguoiDung);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("tenNguoiDung");
            }

        } catch (Exception e) {
            System.out.println("Lỗi getTenNhanVien: " + e.getMessage());
        }

        return null;
    }

    @Override
    public int insertReturnId(HoaDon hd, Connection conn) {
        int maHD = -1;

        try {
            String sql = "INSERT INTO HoaDon(maNguoiDung, trangThai, tongTien, ngayTao) VALUES (?,?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, hd.getMaNguoiDung());
            ps.setBoolean(2, hd.isTrangThai());
            ps.setDouble(3, hd.getTongTien());
            ps.setTimestamp(4, hd.getNgayTao());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                maHD = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return maHD;
    }

    @Override
    public int insertHoaDon(int maNguoiDung, double tongTien) {
        int maHD = -1;

        try {
            Connection conn = JDBC.getConnection();

            String sql = "INSERT INTO HoaDon(maNguoiDung, trangThai, tongTien) VALUES (?,1,?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, maNguoiDung);
            ps.setDouble(2, tongTien);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                maHD = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return maHD;
    }

    @Override
    public void insertHoaDonChiTiet(int maHD, GioHang g) {
        try {
            Connection conn = JDBC.getConnection();

            String sql = "INSERT INTO HoaDonChiTiet(maHoaDon, maDoUong, donGia, soLuong) VALUES (?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, maHD);
            ps.setInt(2, g.getMaDoUong());
            ps.setDouble(3, g.getDonGia());
            ps.setInt(4, g.getSoLuong());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public HoaDon selectById(int maHoaDon) {
        HoaDon hd = null;
        String sql = "SELECT * FROM HoaDon WHERE maHoaDon = ?";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maHoaDon);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                hd = new HoaDon();
                hd.setMaHoaDon(rs.getInt("maHoaDon"));
                hd.setMaNguoiDung(rs.getInt("maNguoiDung"));
                hd.setTongTien(rs.getInt("tongTien"));
                hd.setTrangThai(rs.getBoolean("trangThai"));
                hd.setNgayTao(rs.getTimestamp("ngayTao"));
                // Nếu có ghi chú trong DB, hd.setGhiChu(rs.getString("ghiChu"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hd;
    }
}