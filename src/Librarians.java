import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Librarians extends Users {

    private JTextField status = new JTextField("", 5);
    private JTextField name = new JTextField("", 5);
    private JTextField sname = new JTextField("", 5);
    private JTextField address = new JTextField("", 5);
    private JFormattedTextField phone;
    private JLabel lab0 = new JLabel("Status: ");
    private JLabel lab1 = new JLabel("Name:");
    private JLabel lab2 = new JLabel("Surname:");
    private JLabel lab3 = new JLabel("Adress:");
    private JLabel lab4 = new JLabel("Phone number:");
    private JButton button = new JButton("Ok");
    private JButton cancel = new JButton("Cancel");
    private String[] items = {"Librarian", "Faculty", "Student"};
    private JComboBox list = new JComboBox(items);

    Librarians() throws SQLException{
        ChoiceBox();
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.setConnection();
        statement = connection.createStatement();
    }
    Statement statement;
    ResultSet resultSet;

    public String[] addPerson(String name, String surname, String address, int phone, String type) throws SQLException {
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










    private void createPage(){

        this.setBounds(0, 0, 640, 420);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = this.getContentPane(); //field for buttons;
        container.setLayout(new GridLayout(6, 0, 2, 2));

        container.add(lab0);
        container.add(list);

        container.add(lab1);
        container.add(name);

        container.add(lab2);
        container.add(sname);

        container.add(lab3);
        container.add(address);

        container.add(lab4);
        try {
            MaskFormatter phoneFormatter = new MaskFormatter("+#-###-###-##-##");
            phoneFormatter.setPlaceholderCharacter('_');
            phone = new JFormattedTextField(phoneFormatter);
            phone.setColumns(16);
            container.add(phone);
        } catch (Exception e) {
            e.printStackTrace();
        }

        button.addActionListener(new Event());
        container.add(button, BorderLayout.EAST);

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ChoiceBox();

                dispose();

            }
        });
        container.add(cancel);
    }

    protected class Event implements ActionListener {                       //Данные будут непосредственно отправляться в БД

        @Override
        public void actionPerformed(ActionEvent e) {
            String message = "Status: " + list.getSelectedItem() + "\n";
            message += "Name: " + name.getText() + "\n";
            message += "Surname: " + sname.getText() + "\n";
            message += "Address: " + address.getText() + "\n";
            message += "Phone: " + phone.getText();
            JOptionPane.showMessageDialog(null, message, "User Card", JOptionPane.PLAIN_MESSAGE);

        }
    }


    private void ChoiceBox(){
        JFrame frame = new JFrame("Glib");
        frame.setBounds(0,0, 250,250);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3,3,2,2));

        JButton add = new JButton("Add a book");
        JButton modify = new JButton("Modify Library");
        JButton create = new JButton("Create user");


        frame.add(add);
        frame.add(modify);
        frame.add(create);
        frame.setVisible(true);

        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                createPage();
            }
        });
    }
}
