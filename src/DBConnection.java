import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    Connection connection;
    String userName = "root";
    String password = "1234";
    String connectionUrl = "jdbc:mysql://localhost:3306/mysql?autoReconnect=true&useSSL=false";

    public DBConnection() {
        try

        {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded successfully");
        } catch (
                Exception e)

        {
            System.out.println("Unable to load driver");
        }
    }

    public Connection setConnection() {
        try {
            connection = DriverManager.getConnection(connectionUrl, userName, password);
            System.out.println("DataBase connection successfully ");
            return connection;
        } catch (Exception e) {
            System.out.println("DB connection Error");
            return null;
        }
    }
}