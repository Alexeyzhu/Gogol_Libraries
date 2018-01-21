import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    Librarians() {
        super();
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
                dispose();
                new LogIn().setVisible(true);
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
}
