package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDbConnection implements ConnectionInterface{

    private static final String URL = "jdbc:mysql://localhost:3306/babor_sir_class";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public Connection getConnection() throws SQLException {
        System.out.println("inside MySQL connection");
        return DriverManager.getConnection(URL,USER,PASSWORD);
    }

}
