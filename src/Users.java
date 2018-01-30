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
        // Convert to lower case
        name = name.toLowerCase();
        surname = surname.toLowerCase();

        // create login and password
        String login = name.substring(0, 1) + "." + surname;
        String password = Integer.toString(hashFunction(name, surname));
        String[] loginData = {login, password};
        printLoginData(loginData);
        return loginData;
    }

    // delete later, just for debug
    private static void printLoginData(String[] data){
        System.out.println(data[0]);
        System.out.println(data[1]);
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
