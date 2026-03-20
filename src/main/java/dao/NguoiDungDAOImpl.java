package dao;

import entity.NguoiDung;
import util.JDBCHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NguoiDungDAOImpl implements NguoiDungDAO {

    private NguoiDung readFromResultSet(ResultSet rs) throws Exception {
        NguoiDung nd = new NguoiDung();
        nd.setMaNguoiDung(rs.getInt("maNguoiDung")); // FIX
        nd.setTenNguoiDung(rs.getString("tenNguoiDung"));
        nd.setEmail(rs.getString("email"));
        nd.setTenDangNhap(rs.getString("tenDangNhap"));
        nd.setMatKhau(rs.getString("matKhau"));
        nd.setVaiTro(rs.getString("vaiTro"));
        nd.setTrangThai(rs.getBoolean("trangThai"));
        nd.setHinhAnh(rs.getString("hinhAnh")); // FIX
        return nd;
    }

    private List<NguoiDung> selectBySql(String sql, Object... args) {
        List<NguoiDung> list = new ArrayList<>();
        ResultSet rs = null;

        try {
            rs = JDBCHelper.query(sql, args);

            while (rs.next()) {
                list.add(readFromResultSet(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.getStatement().getConnection().close(); // 🔥 FIX LEAK
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    @Override
    public void insert(NguoiDung nd) {
        String sql =
                "INSERT INTO NguoiDung " +
                        "(tenNguoiDung, email, tenDangNhap, matKhau, vaiTro, trangThai, hinhAnh) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";

        JDBCHelper.update(sql,
                nd.getTenNguoiDung(),
                nd.getEmail(),
                nd.getTenDangNhap(),
                nd.getMatKhau(),
                nd.getVaiTro(),
                nd.isTrangThai(),   // FIX
                nd.getHinhAnh()     // FIX
        );
    }

    @Override
    public void update(NguoiDung nd) {
        String sql =
                "UPDATE NguoiDung SET " +
                        "tenNguoiDung = ?, " +
                        "email = ?, " +
                        "tenDangNhap = ?, " +
                        "matKhau = ?, " +
                        "vaiTro = ?, " +
                        "trangThai = ?, " +
                        "hinhAnh = ? " +
                        "WHERE maNguoiDung = ?";

        JDBCHelper.update(sql,
                nd.getTenNguoiDung(),
                nd.getEmail(),
                nd.getTenDangNhap(),
                nd.getMatKhau(),
                nd.getVaiTro(),
                nd.isTrangThai(),   // FIX
                nd.getHinhAnh(),    // FIX
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
        return selectBySql("SELECT * FROM NguoiDung");
    }

    @Override
    public List<NguoiDung> findByTen(String ten) {
        String sql = "SELECT * FROM NguoiDung WHERE tenNguoiDung LIKE ?";
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