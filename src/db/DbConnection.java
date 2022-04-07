package db;

public class DbConnection {

    public static ConnectionInterface getConnection(){
        System.out.println("inside DbConnection");
        ConnectionInterface connectionInterface = new SQLiteDbConnection();
        return connectionInterface;
    }
}
