package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import config.AppConfig;

/**
 * A utility class responsible for managing the creation and closure of
 * database connections. It uses the database URL configured in
 * {@link AppConfig}.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */
public class ConnectionFactory {

    /**
     * Establishes and returns a new database connection using the URL specified
     * in {@link AppConfig#DB_URL}.
     *
     * @return A {@link Connection} object representing the connection to the
     *         database.
     * @throws SQLException If a database access error occurs or the URL is invalid.
     */
    public static Connection getConnection() throws SQLException {
        String url = AppConfig.DB_URL;
        return DriverManager.getConnection(url);
    }

    /**
     * Closes the provided database connection. It checks if the connection is
     * not null and is currently open before attempting to close it. If an
     * {@link SQLException} occurs during the closing process, an error message
     * is printed to the console, and the application exits with a status code of 1.
     *
     * @param conn The {@link Connection} object to close.
     */
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