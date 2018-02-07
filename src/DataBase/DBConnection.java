package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
public class DBConnection {
    Connection connection;

    // Put your URL here
    String connectionUrl = "jdbc:sqlite:gogollib.db";

    /**
     * Loader of drivers
     */

    public DBConnection() {
        try
        {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e)
        {

        }
    }

    /**
     * set connection to database
     * @return statement which is necessary for queries to database
     */

    public Connection setConnection() {
        try {
            connection = DriverManager.getConnection(connectionUrl);
            //System.out.println("DataBase connection successfully ");
            return connection;
        } catch (Exception e) {
            System.out.println("DB connection Error");
            return null;
        }
    }
}