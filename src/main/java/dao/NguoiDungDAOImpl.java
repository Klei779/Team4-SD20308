package dao;

import entity.NguoiDung;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.JDBCHelper;

public class NguoiDungDAOImpl implements NguoiDungDAO {

    // ==========================
    // READ FROM RESULTSET
    // ==========================
    private NguoiDung readFromResultSet(ResultSet rs) throws Exception {
        NguoiDung nd = new NguoiDung();
        nd.setMaNguoiDung(rs.getString("maNguoiDung"));
        nd.setTenNguoiDung(rs.getString("tenNguoiDung"));
        nd.setEmail(rs.getString("email"));
        nd.setTenDangNhap(rs.getString("tenDangNhap"));
        nd.setMatKhau(rs.getString("matKhau"));
        nd.setVaiTro(rs.getString("vaiTro"));
        nd.setTrangThai(rs.getBoolean("trangThai"));
        nd.setHinhanh(rs.getString("hinhAnh"));
        return nd;
    }

    private List<NguoiDung> selectBySql(String sql, Object... args) {
        List<NguoiDung> list = new ArrayList<>();
        try (ResultSet rs = JDBCHelper.query(sql, args)) {
            while (rs.next()) {
                list.add(readFromResultSet(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ==========================
    // CRUD
    // ==========================
    @Override
    public void insert(NguoiDung nd) {
        String sql = """
            INSERT INTO NguoiDung
            (tenNguoidung, email, tenDangNhap, matKhau, vaiTro, trangThai, hinhAnh)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        JDBCHelper.update(sql,
                nd.getTenNguoiDung(),
                nd.getEmail(),
                nd.getTenDangNhap(),
                nd.getMatKhau(),
                nd.getVaiTro(),
                nd.getTrangThai(),
                nd.getHinhanh()
        );
    }

    @Override
    public void update(NguoiDung nd) {
        String sql = """
            UPDATE NguoiDung SET
                tenNguoidung = ?,
                email = ?,
                tenDangNhap = ?,
                matKhau = ?,
                vaiTro = ?,
                trangThai = ?,
                hinhAnh = ?
            WHERE maNguoiDung = ?
        """;

        JDBCHelper.update(sql,
                nd.getTenNguoiDung(),
                nd.getEmail(),
                nd.getTenDangNhap(),
                nd.getMatKhau(),
                nd.getVaiTro(),
                nd.getTrangThai(),
                nd.getHinhanh(),
                nd.getMaNguoiDung()
        );
    }

    @Override
    public void delete(int maNguoiDung) {
        String sql = "DELETE FROM NguoiDung WHERE maNguoiDung = ?";
        JDBCHelper.update(sql, maNguoiDung);
    }

    @Override
    public NguoiDung findById(int maNguoiDung) {
        String sql = "SELECT * FROM NguoiDung WHERE maNguoiDung = ?";
        List<NguoiDung> list = selectBySql(sql, maNguoiDung);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<NguoiDung> findAll() {
        String sql = "SELECT * FROM NguoiDung";
        return selectBySql(sql);
    }

    // ==========================
    // TÌM KIẾM
    // ==========================

    @Override
    public List<NguoiDung> findByTen(String ten) {
        String sql = "SELECT * FROM NguoiDung WHERE tenNguoidung LIKE ?";
        return selectBySql(sql, "%" + ten + "%");
    }

    @Override
    public List<NguoiDung> findByTrangThai(boolean trangThai) {
        String sql = "SELECT * FROM NguoiDung WHERE trangThai = ?";
        return selectBySql(sql, trangThai);
    }

    @Override
    public List<NguoiDung> findByVaiTro(String vaiTro) {
        String sql = "SELECT * FROM NguoiDung WHERE vaiTro = ?";
        return selectBySql(sql, vaiTro);
    }
}