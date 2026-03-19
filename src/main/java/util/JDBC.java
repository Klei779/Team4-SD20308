package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBC {
    private static final String URL =
            "jdbc:sqlserver://localhost:1433;databaseName=QuanLyQuanNuoc_Test1;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASS = "123456"; // sửa theo máy bạn

    public static Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi kết nối DB!");
        }
    }
}
