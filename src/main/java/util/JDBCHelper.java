package util;

import java.sql.*;

public class JDBCHelper {

    // =============================
    // UPDATE
    // =============================
    public static int update(String sql, Object... args) {
        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            setParams(ps, args);
            return ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Lỗi update", e);
        }
    }

    // =============================
    // QUERY (ResultSet)
    // =============================
    public static ResultSet query(String sql, Object... args) {
        try {
            Connection conn = JDBC.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            setParams(ps, args);

            return ps.executeQuery();

        } catch (Exception e) {
            throw new RuntimeException("Lỗi query", e);
        }
    }

    // =============================
    // VALUE
    // =============================
    public static Object value(String sql, Object... args) {
        ResultSet rs = null;
        try {
            rs = query(sql, args);
            if (rs.next()) {
                return rs.getObject(1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) {
                    rs.getStatement().getConnection().close(); // 🔥 đóng tại đây
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // =============================
    // SET PARAM
    // =============================
    private static void setParams(PreparedStatement ps, Object... args) throws Exception {
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i + 1, args[i]);
        }
    }
}