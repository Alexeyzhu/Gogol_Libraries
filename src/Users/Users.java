package Users;

import DataBase.DBConnection;
import org.omg.CORBA.OBJECT_NOT_EXIST;
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
    private static Statement statement;
    private static ResultSet resultSet;

    static {
        try {
            statement = new DBConnection().setConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    Users() throws SQLException {
        super("Glib");
    }

    /**
     * User will be enter to the library only if
     * password and login right. GUI will get the level of access of user
     * Use it in order to pass users into system
     *
     * @param login    login of user
     * @param password password of user to check
     * @return level of access library or patron
     * @throws SQLException, OBJECT_NOT_EXIST
     */
    public String getType(String login, String password) throws SQLException, OBJECT_NOT_EXIST {

        if (checkLoginPassword(login, password)) {
            String type = "";
            resultSet = statement.executeQuery("SELECT type FROM users " +
                    "WHERE id = '" + getID(login, password) + "'");
            while (resultSet.next()) {
                type = resultSet.getString("type");
            }
            return type;
        } else {
            throw new OBJECT_NOT_EXIST("There is no such person");
        }

    }

    /**
     * Returns the id of user according to its login and password
     *
     * @param login    login of user
     * @param password password of user to check
     * @return id of person in the database
     * @throws SQLException
     */
    public int getID(String login, String password) throws SQLException {
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

    /**
     * Checks person's login and password if they are in the database
     * and suitable to each other
     *
     * @param login    login of user
     * @param password password of user to check
     * @return true - if login and password are in system and belong to one person
     * false - if password do not suitable to the login or login absent in system
     * @throws SQLException
     */
    public boolean checkLoginPassword(String login, String password) throws SQLException {
        int DBpass = 0;
        int pass = Integer.parseInt(password);

        resultSet = statement.executeQuery("SELECT * FROM users " +
                "WHERE login = '" + login + "'");
        while (resultSet.next()) {
            DBpass = resultSet.getInt("password");
        }

        return DBpass == pass;
    }
}
