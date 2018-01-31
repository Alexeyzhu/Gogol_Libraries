import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
public class DBConnection {
    Connection connection;
   // String userName = "root";
   // String password = "1234";

    // Put your URL here
    String connectionUrl = "jdbc:sqlite:C:/Users/Алексей/Documents/GitHub/Gogol_Libraries/sources/gogollib.db";

    public DBConnection() {
        try

        {
            Class.forName("org.sqlite.JDBC");
            System.out.println("Driver loaded successfully");
        } catch (
                Exception e)
        {
            System.out.println("Unable to load driver");
        }
    }

    public Connection setConnection() {
        try {
            connection = DriverManager.getConnection(connectionUrl);
            System.out.println("DataBase connection successfully ");
            return connection;
        } catch (Exception e) {
            System.out.println("DB connection Error");
            return null;
        }
    }
}