import javax.swing.*;
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

    public String[] createPerson(String name, String surname, String address, int phone, String type) throws SQLException {
        String[] dataLogin = generateLogin(name, surname);
        statement.executeUpdate("INSERT library.users (" +
                "login, password, name, surname, address, phone, type) " +
                " VALUES ('" + dataLogin[0] + "','" + dataLogin[1] + "','" + name + "'" +
                ",'" + surname + "','" + address + "','" + phone + "','" + type + "')");
        return dataLogin;
    }

    private String[] generateLogin(String name, String surname) throws SQLException {
        String login = name.substring(0, 1).toLowerCase() + "." + surname.toLowerCase();
        isLogin(login);
        String password = Integer.toString(hashFunction(name, surname));
        String[] loginData = new String[2];
        loginData[0] = login;
        loginData[1] = password;
        System.out.println(login);
        System.out.println(password);
        return loginData;
    }

    private int hashFunction(String name, String surname) {
        String data = name + surname;
        while (data.length() < 5) {
            data += data;
        }
        int hash = data.hashCode();
        if (hash < 0) {
            hash = hash * -1;
        }
        return hash;
    }

    private boolean isLogin(String login) throws SQLException {
        boolean status = true;
        String DBLogin = "fgg";
        resultSet = statement.executeQuery("SELECT * FROM library.users " +
                "WHERE login='" + login + "'");
        while (resultSet.next()) {
            DBLogin = resultSet.getNString("login");
        }
        if (DBLogin == null) {
            status = false;
        }
        return status;
    }


}
