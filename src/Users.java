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

    public String[] createPerson(String name, String surname, String address, int phone, String type) throws SQLException {
        int result = 0;
        //check if there is exist already this person
        resultSet = statement.executeQuery("SELECT EXISTS(SELECT id FROM users " +
                "WHERE name = '" + name + "' AND surname = '" + surname + "')");

        while (resultSet.next()) {
            result = resultSet.getInt(1);
        }

        if (result == 1) {
            System.out.println("There is already exist this person");
            return null;
        } else {
            String[] data = generateLogin(name, surname);
            statement.executeUpdate("INSERT INTO users (login, password, name, surname, address, phone, type) " +
                    "VALUES ('" + data[0] + "','" + data[1] + "','" + name + "','" + surname + "','" + address + "'," + phone + ",'" + type + "')");
            return data;
        }

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
            return true;
        } else {
            return false;
        }
    }

    private String[] generateLogin(String name, String surname) throws SQLException {
        // Convert to lower case
        name = name.toLowerCase();
        surname = surname.toLowerCase();

        // create login and password
        String login = name.substring(0, 1).toLowerCase() + "." + surname.toLowerCase();
        String password = Integer.toString(hashFunction(name, surname));
        String[] loginData = {login, password};
        printLoginData(loginData);

        return loginData;
    }

    // delete it later
    private void printLoginData(String[] loginData) {
        System.out.println(loginData[0] + "\n" + loginData[1]);
    }

    private int hashFunction(String name, String surname) {
        final int MAX_PASSWORD_LENGTH = 8;
        final int BASE_OF_ARITHMETIC = 10;

        String data = name + surname;
        while (data.length() < 10) {
            data += data;
        }
        int hash = Math.abs(data.hashCode());

        return (int) (hash % Math.pow(BASE_OF_ARITHMETIC, MAX_PASSWORD_LENGTH));
    }

    Users() throws SQLException {
        super("Glib");
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.setConnection();
        statement = connection.createStatement();
    }

}
