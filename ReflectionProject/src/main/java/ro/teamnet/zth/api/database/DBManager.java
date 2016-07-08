package ro.teamnet.zth.api.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by user on 7/8/2016.
 */
public class DBManager {
    private static final String CONNECTION_STRING = "jdbc:oracle:thin:@" + DBProperties.IP + ":" + DBProperties.PORT;
    private DBManager() throws UnsupportedOperationException {

    }
    private static void registerDriver() {
        try {
            Class.forName(DBProperties.DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() {
        DBManager.registerDriver();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DBManager.CONNECTION_STRING, DBProperties.USER, DBProperties.PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    public static boolean checkConnection(Connection connection) {
        try (Statement stmt = connection.createStatement()) {
            return stmt.execute("SELECT 1 FROM DUAL");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
