import Users.Librarians;
import Users.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LogIn extends JFrame {

    private JLabel llab = new JLabel("LogIn");
    private JLabel plab = new JLabel("Password");
    private JLabel status = new JLabel("Status");
    private JPasswordField pass = new JPasswordField("", 5);
    private JTextField login = new JTextField("", 5);
    private JButton ok = new JButton("Ok");
    private JButton cancel = new JButton("Cancel");
    private String[] items = {"Librarian", "Users.Faculty", "Users.Student"};
    private JComboBox list = new JComboBox(items);


    LogIn() {

        super("GLib");
        this.setBounds(0, 0, 320, 320);
//        this.setLocationRelativeTo(null);
        this.setLocationByPlatform(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(8, 6, 2, 2));

        container.add(status);
        container.add(list);

        container.add(llab);
        container.add(login);

        container.add(plab);
        container.add(pass);


        container.setVisible(true);

        ok.addActionListener(new ActionListener() {

                                 @Override
                                 public void actionPerformed(ActionEvent e)  {
                                     try {
                                         Exist();
                                     } catch (SQLException e1) {
                                         e1.printStackTrace();
                                     }

                                 }

                                 private void Exist() throws SQLException {
                                     if (isLibrarian() && login.getText().equals("admin") && pass.getText().equals("admin")) {
                                         Librarians user = new Librarians();
                                         user.setVisible(true);
                                         setVisible(false);
                                     } else if (isFaculty() && login.getText().equals("faculty") && pass.getText().equals("faculty")) {

                                     } else if (isStudent() && login.getText().equals("student") && pass.getText().equals("student")) {
                                         Student users = new Student(login.getText());
                                     } else JOptionPane.showMessageDialog(null, "Incorrect Status, LogIn or Password");


                                 }

                                 private boolean isStudent() {
                                     if (list.getSelectedItem().equals("Users.Student")) {
                                         return true;
                                     }
                                     return false;
                                 }                                                      //Обращение к БД и проверка, кто есть кто

                                 private boolean isFaculty() {
                                     if (list.getSelectedItem().equals("Users.Faculty")) {
                                         return true;
                                     }
                                     return false;
                                 }

                                 private boolean isLibrarian() {
                                     if (list.getSelectedItem().equals("Librarian")) {
                                         return true;
                                     }
                                     return false;
                                 }

                             }
        );
        container.add(ok);

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        container.add(cancel);


    }
}
