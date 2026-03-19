package util;

import java.sql.*;

public class JDBCHelper {

    public static int update(String sql, Object... args) {
        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            setParams(ps, args);
            return ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // =============================
    // 🔥 QUERY (SELECT)
    // =============================
    public static ResultSet query(String sql, Object... args) {
        try {
            Connection conn = JDBC.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            setParams(ps, args);
            return ps.executeQuery();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // =============================
    // 🔥 LẤY 1 GIÁ TRỊ
    // =============================
    public static Object value(String sql, Object... args) {
        try (ResultSet rs = query(sql, args)) {
            if (rs.next()) {
                return rs.getObject(0);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // =============================
    // 🔧 SET PARAM
    // =============================
    private static void setParams(PreparedStatement ps, Object... args) throws Exception {
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i + 1, args[i]);
        }
    }

}
