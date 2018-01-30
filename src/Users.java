import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Users extends JFrame {

    public String[] createPerson(String name, String surname, String address, int phone, String type) throws SQLException {


        return generateLogin(name, surname);
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

    Users() {
        super("Glib");
    }

}
