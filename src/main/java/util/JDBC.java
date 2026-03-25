package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBC {

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyQuanNuoc_Test6;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASS = "123";

    public static Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            conn.setAutoCommit(true);
            return conn;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi kết nối DB!", e);
        }
    }
}