import org.sqlite.core.DB;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Users extends JFrame {
    Statement statement;
    ResultSet resultSet;

    Users() throws SQLException {
        super("Glib");
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.setConnection();
        statement = connection.createStatement();
    }

    // check who is the user
    public String getType(String login, String password) throws SQLException {

        if (checkLoginPassword(login, password)) {
            String type = "";
            resultSet = statement.executeQuery("SELECT type FROM users " +
                    "WHERE id = '" + getID(login, password) + "'");
            while (resultSet.next()){
                type = resultSet.getString("type");
            }
            return type;
        } else {
            System.out.println("There is no such person");
            return null;
        }

    }

    private int getID(String login, String password) throws SQLException {
        int id = 0;
        int pass = Integer.parseInt(password);
        resultSet = statement.executeQuery("SELECT id FROM users " +
                "WHERE login = '" + login + "' AND  password = '" + pass + "'");
        while (resultSet.next()) {
            id = resultSet.getInt("id");
        }
        System.out.println("There is ID of user : " + id);
        return id;
    }


    public boolean checkLoginPassword(String login, String password) throws SQLException {
        int DBpass = 0;
        int pass = Integer.parseInt(password);
        resultSet = statement.executeQuery("SELECT * FROM users " +
                "WHERE login = '" + login + "'");
        while (resultSet.next()) {
            DBpass = resultSet.getInt("password");
        }

        if (DBpass == pass) {
            System.out.println("login and password are correct");
            return true;
        } else {
            System.out.println("check login or password");
            return false;
        }
    }
}
