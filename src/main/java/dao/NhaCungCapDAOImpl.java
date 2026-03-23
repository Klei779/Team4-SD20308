package dao;


import entity.NhaCungCap;
import util.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NhaCungCapDAOImpl implements NhaCungCapDAO {

    @Override
    public void insert(NhaCungCap ncc) {
        String sql = "INSERT INTO NhaCungCap(tenNCC, dienThoai, diaChi) VALUES (?, ?, ?)";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ncc.getTenNhaCungCap());
            ps.setString(2, ncc.getDienThoai());
            ps.setString(3, ncc.getDiaChi());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(NhaCungCap ncc) {
        String sql = "UPDATE NhaCungCap SET tenNCC = ?, dienThoai = ?, diaChi = ? WHERE maNCC = ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ncc.getTenNhaCungCap());
            ps.setString(2, ncc.getDienThoai());
            ps.setString(3, ncc.getDiaChi());
            ps.setInt(4, ncc.getMaNhaCungCap());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(NhaCungCap ncc) {
        String sql = "DELETE FROM NhaCungCap WHERE maNCC = ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ncc.getMaNhaCungCap());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<NhaCungCap> findAll() {
        List<NhaCungCap> list = new ArrayList<>();
        String sql = "SELECT * FROM NhaCungCap";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                NhaCungCap ncc = new NhaCungCap();
                ncc.setMaNhaCungCap(rs.getInt("maNCC"));
                ncc.setTenNhaCungCap(rs.getString("tenNCC"));
                ncc.setDienThoai(rs.getString("dienThoai"));
                ncc.setDiaChi(rs.getString("diaChi"));

                list.add(ncc);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<NhaCungCap> findByName(String tenNhaCungCap) {
        List<NhaCungCap> list = new ArrayList<>();
        String sql = "SELECT * FROM NhaCungCap WHERE tenNCC LIKE ?";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + tenNhaCungCap + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                NhaCungCap ncc = new NhaCungCap();

                ncc.setMaNhaCungCap(rs.getInt("maNCC"));
                ncc.setTenNhaCungCap(rs.getString("tenNCC"));
                ncc.setDienThoai(rs.getString("dienThoai"));
                ncc.setDiaChi(rs.getString("diaChi"));

                list.add(ncc);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
