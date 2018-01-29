import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Users extends JFrame {

    public String[] createPerson(String name, String surname, String address, int phone, String type) {


        return generateLogin(name, surname);
    }

    private String[] generateLogin(String name, String surname) {
        String login = name.substring(0, 1) + "." + surname;
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
        while (data.length() < 10) {
            data += data;
        }
        int hash = data.hashCode();
        if (hash < 0) {
            hash = hash * -1;
        }
        return hash;
    }

    Users() {
        super("Glib");
    }

}
