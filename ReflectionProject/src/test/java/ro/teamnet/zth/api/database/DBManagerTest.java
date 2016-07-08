package ro.teamnet.zth.api.database;

import org.junit.Test;

import java.sql.Connection;

/**
 * Created by user on 7/8/2016.
 */
public class DBManagerTest {
    @Test
    public void testGetConnection() {
        assert DBManager.getConnection() != null;
    }
    @Test
    public void testCheckConnection() {
        Connection conn = DBManager.getConnection();
        assert conn != null && DBManager.checkConnection(conn);
    }
}
