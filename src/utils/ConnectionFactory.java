package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import config.AppConfig;

public class ConnectionFactory {

    public static Connection getConnection() throws SQLException {
        String url = AppConfig.DB_URL;
        return DriverManager.getConnection(url);
    }

    public static void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.exit(1);
        }
    }
}
