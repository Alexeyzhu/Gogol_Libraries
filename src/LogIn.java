import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogIn extends JFrame {

    private JLabel llab = new JLabel("LogIn");
    private JLabel plab = new JLabel("Password");
    private JPasswordField pass = new JPasswordField("", 5);
    private JTextField login = new JTextField("", 5);
    private JButton ok = new JButton("Ok");
    private JButton cancel = new JButton("Cancel");
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static int sizeWidth = 800;
    public static int sizeHeight = 600;
    public static int X = (screenSize.width - sizeWidth) / 2;
    public static int Y = (screenSize.height - sizeHeight) / 2;

    LogIn() {

        super("GLib");
        this.setBounds(X, Y, 320, 250);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(6, 6, 2, 2));

        container.add(llab);
        container.add(login);

        container.add(plab);
        container.add(pass);


        container.setVisible(true);

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isExist()) {
                    Users user = new Librarians();
                    user.setVisible(true);
                    setVisible(false);
                }
            }

            private boolean isExist() {
                return true;               //Здесь должно быть обращение к БД
            }
        });
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
