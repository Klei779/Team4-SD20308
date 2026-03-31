package dao;

import entity.PhieuNhapKho;
import util.JDBC;
import util.JDBCHelper;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class PhieuNhapKhoDAOImpl implements PhieuNhapKhoDAO {

    private PhieuNhapKho map(ResultSet rs) throws Exception {
        PhieuNhapKho pnk = new PhieuNhapKho();
        pnk.setMaPhieuNhapKho(rs.getInt("maPhieuNhapKho"));
        pnk.setMaNguoiDung(rs.getInt("maNguoiDung"));
        pnk.setMaNCC(rs.getInt("maNCC"));
        pnk.setNgayNhapKho(rs.getTimestamp("ngayNhapKho"));
        pnk.setTongTien(rs.getInt("tongTien")); // INT
        pnk.setGhiChu(rs.getString("ghiChu"));
        return pnk;
    }

    // ===== INSERT (TRẢ ID) =====
    @Override
    public int insert(PhieuNhapKho pnk) {

        String sql =
                "INSERT INTO PhieuNhapKho (maNguoiDung, maNCC, tongTien, ghiChu) VALUES (?, ?, ?, ?)";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, pnk.getMaNguoiDung());
            ps.setInt(2, pnk.getMaNCC());
            ps.setInt(3, pnk.getTongTien());
            ps.setString(4, pnk.getGhiChu());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    // ===== UPDATE TỔNG TIỀN =====
    @Override
    public void updateTongTien(int maPN, int tongTien) {

        String sql = "UPDATE PhieuNhapKho SET tongTien=? WHERE maPhieuNhapKho=?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, tongTien);
            ps.setInt(2, maPN);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== LOAD NCC =====
    @Override
    public List<Map<String, Object>> getAllNCC() {

        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT maNCC, tenNCC FROM NhaCungCap";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", rs.getInt("maNCC"));
                map.put("ten", rs.getString("tenNCC"));
                list.add(map);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ===== LOAD NGUYÊN LIỆU =====
    @Override
    public List<Map<String, Object>> getAllNguyenLieu() {

        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT maNguyenLieu, tenNguyenLieu FROM NguyenLieu";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", rs.getInt("maNguyenLieu"));
                map.put("ten", rs.getString("tenNguyenLieu"));
                list.add(map);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ===== FIND ALL =====
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

    // ===== GET TÊN =====
    @Override
    public String getTenNhanVien(int maNguoiDung) {
        String sql = "SELECT tenNguoiDung FROM NguoiDung WHERE maNguoiDung=?";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maNguoiDung);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("tenNguoiDung");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "N/A";
    }

    @Override
    public String getTenNCC(int maNCC) {
        String sql = "SELECT tenNCC FROM NhaCungCap WHERE maNCC=?";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maNCC);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("tenNCC");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "N/A";
    }
    @Override
    public List<PhieuNhapKho> findByNCC(int maNCC) {

        List<PhieuNhapKho> list = new ArrayList<>();

        String sql = "SELECT * FROM PhieuNhapKho WHERE maNCC = ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maNCC);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                PhieuNhapKho p = new PhieuNhapKho();

                p.setMaPhieuNhapKho(rs.getInt("maPhieuNhapKho"));
                p.setMaNguoiDung(rs.getInt("maNguoiDung"));
                p.setMaNCC(rs.getInt("maNCC"));
                p.setNgayNhapKho(rs.getTimestamp("ngayNhapKho")); // nhớ dùng Timestamp
                p.setTongTien(rs.getInt("tongTien"));
                p.setGhiChu(rs.getString("ghiChu")); // nếu có

                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    @Override
    public List<PhieuNhapKho> findByDate(Date from, Date to) {

        List<PhieuNhapKho> list = new ArrayList<>();

        String sql = "SELECT * FROM PhieuNhapKho WHERE ngayNhapKho BETWEEN ? AND ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, new java.sql.Timestamp(from.getTime()));
            ps.setTimestamp(2, new java.sql.Timestamp(to.getTime()));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                PhieuNhapKho p = new PhieuNhapKho();

                p.setMaPhieuNhapKho(rs.getInt("maPhieuNhapKho"));
                p.setMaNguoiDung(rs.getInt("maNguoiDung"));
                p.setMaNCC(rs.getInt("maNCC"));
                p.setNgayNhapKho(rs.getTimestamp("ngayNhapKho")); // chuẩn Timestamp
                p.setTongTien(rs.getInt("tongTien"));
                p.setGhiChu(rs.getString("ghiChu"));

                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    @Override
    public List<PhieuNhapKho> findByNguoiDung(int maNguoiDung) {

        List<PhieuNhapKho> list = new ArrayList<>();

        String sql = "SELECT * FROM PhieuNhapKho WHERE maNguoiDung = ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maNguoiDung);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                PhieuNhapKho p = new PhieuNhapKho();

                p.setMaPhieuNhapKho(rs.getInt("maPhieuNhapKho"));
                p.setMaNguoiDung(rs.getInt("maNguoiDung"));
                p.setMaNCC(rs.getInt("maNCC"));
                p.setNgayNhapKho(rs.getTimestamp("ngayNhapKho")); // dùng Timestamp
                p.setTongTien(rs.getInt("tongTien"));
                p.setGhiChu(rs.getString("ghiChu"));

                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public PhieuNhapKho findById(int id) {
        String sql = "SELECT * FROM PhieuNhapKho WHERE MaPhieuNhapKho = ?";

        try (ResultSet rs = JDBCHelper.query(sql, id)) {
            if (rs.next()) {
                PhieuNhapKho p = new PhieuNhapKho();

                p.setMaPhieuNhapKho(rs.getInt("MaPhieuNhapKho"));
                p.setMaNguoiDung(rs.getInt("MaNguoiDung"));
                p.setMaNCC(rs.getInt("MaNCC"));
                p.setNgayNhapKho(rs.getTimestamp("NgayNhapKho"));
                p.setTongTien(rs.getInt("TongTien"));
                p.setGhiChu(rs.getString("GhiChu"));

                return p;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}