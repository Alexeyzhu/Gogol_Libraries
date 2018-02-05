/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package back.users;

import java.sql.Connection;
import java.sql.DriverManager;
public class DBConnection {
    Connection connection;
   // String userName = "root";
   // String password = "1234";

    // Put your URL here
    String connectionUrl = "jdbc:sqlite:gogollib.db";

    /**
     * Loader of drivers
     */

    public DBConnection() {
        try
        {
            Class.forName("org.sqlite.JDBC");
            System.out.println("Driver loaded successfully");
        } catch (Exception e)
        {
            System.out.println("Unable to load driver");
        }
    }

    /**
     * set connection to database
     * @return statement which is necessary for queries to database
     */

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
