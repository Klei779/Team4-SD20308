package dao;

import entity.NguoiDung;
import util.JDBC;
import util.JDBCHelper;

import javax.management.Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
        Connection conn = null;

        try {
            conn = JDBC.getConnection();
            conn.setAutoCommit(false);

            // 1. Xóa HoaDonChiTiet
            String sql1 =
                    "DELETE FROM HoaDonChiTiet " +
                            "WHERE maHoaDon IN (" +
                            "   SELECT maHoaDon FROM HoaDon WHERE maNguoiDung = ?" +
                            ")";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setInt(1, maNguoiDung);
            ps1.executeUpdate();

            // 2. Xóa HoaDon
            String sql2 = "DELETE FROM HoaDon WHERE maNguoiDung = ?";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setInt(1, maNguoiDung);
            ps2.executeUpdate();

            // 3. Xóa PhieuNhapKhoChiTiet
            String sql3 =
                    "DELETE FROM PhieuNhapKhoChiTiet " +
                            "WHERE maPhieuNhapKho IN (" +
                            "   SELECT maPhieuNhapKho FROM PhieuNhapKho WHERE maNguoiDung = ?" +
                            ")";
            PreparedStatement ps3 = conn.prepareStatement(sql3);
            ps3.setInt(1, maNguoiDung);
            ps3.executeUpdate();

            // 4. Xóa PhieuNhapKho
            String sql4 = "DELETE FROM PhieuNhapKho WHERE maNguoiDung = ?";
            PreparedStatement ps4 = conn.prepareStatement(sql4);
            ps4.setInt(1, maNguoiDung);
            ps4.executeUpdate();

            // 5. Xóa NguoiDung
            String sql5 = "DELETE FROM NguoiDung WHERE maNguoiDung = ?";
            PreparedStatement ps5 = conn.prepareStatement(sql5);
            ps5.setInt(1, maNguoiDung);
            ps5.executeUpdate();

            conn.commit();

        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
    public List<NguoiDung> searchByTenOrEmail(String keyword) {
        String sql = "SELECT * FROM NguoiDung WHERE tenNguoiDung LIKE ? OR email LIKE ?";
        return selectBySql(sql, "%" + keyword + "%", "%" + keyword + "%");
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
    @Override
    public NguoiDung login(String username, String password) {
        String sql = "SELECT * FROM NguoiDung WHERE tenDangNhap = ? AND matKhau = ? AND trangThai = 1";
        List<NguoiDung> list = selectBySql(sql, username, password);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public NguoiDung checkLogin(String username, String password) {
        NguoiDung user = null;
        // Câu lệnh SQL: Lấy thông tin người dùng dựa trên user và pass
        String sql = "SELECT * FROM NguoiDung WHERE tenDangNhap = ? AND matKhau = ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new NguoiDung();
                    // Đổ dữ liệu từ Database vào Object Entity
                    user.setMaNguoiDung(rs.getInt("maNguoiDung"));
                    user.setTenDangNhap(rs.getString("tenDangNhap"));
                    user.setMatKhau(rs.getString("matKhau"));
                    user.setTenNguoiDung(rs.getString("tenNguoiDung"));
                    user.setEmail(rs.getString("email"));
                    user.setVaiTro(rs.getString("vaiTro"));
                    user.setHinhAnh(rs.getString("hinhAnh"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user; // Trả về null nếu sai user/pass, trả về object nếu đúng
    }

    public NguoiDung findByTenDangNhap(String tenDangNhap) {
        String sql = "SELECT * FROM NguoiDung WHERE tenDangNhap = ?";
        List<NguoiDung> list = selectBySql(sql, tenDangNhap);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public NguoiDung findByEmail(String email) {
        // Sử dụng TRIM để so khớp chính xác email
        String sql = "SELECT * FROM NguoiDung WHERE LTRIM(RTRIM(email)) = ?";

        List<NguoiDung> list = selectBySql(sql, email.trim());

        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public boolean updatePassword(String email, String newPassword) {
        // Câu lệnh SQL cập nhật mật khẩu dựa trên Email
        String sql = "UPDATE NguoiDung SET matKhau = ? WHERE LTRIM(RTRIM(email)) = ?";

        try {
            // JDBCHelper.update trả về số dòng bị ảnh hưởng (int)
            int rowCount = JDBCHelper.update(sql, newPassword, email.trim());

            // Nếu rowCount > 0 nghĩa là đã cập nhật thành công
            return rowCount > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
