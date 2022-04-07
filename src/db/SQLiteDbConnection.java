package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDbConnection implements ConnectionInterface{

    private static final String URL1 = "jdbc:sqlite://G:/test_app/Java/Student_List_FXMLstyleFull/SQLiteDBn.db";

    @Override
    public Connection getConnection() throws SQLException {

        System.out.println("inside SQL Connection");
        return DriverManager.getConnection(URL1);
    }
}
