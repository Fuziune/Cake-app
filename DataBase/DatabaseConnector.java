package DataBase;

import Settings.Settings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConnector {
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String url = Settings.getPropertyTest("db.url");
            connection = DriverManager.getConnection(url);
        }
        return connection;
    }
}
