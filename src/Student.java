import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Student extends Patrons {
    Student(String name) {

        JFrame frame = new JFrame(name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] columnname = {
                "Document",
                "Checked out date",
                "Return day"
        };

        Calendar cdate = new GregorianCalendar();
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        String[][] data = {
                {"Ex1", format.format(date), format.format(date.getTime() + 1209600000)},
        };
        JTable table = new JTable(data, columnname);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.getContentPane().add(scrollPane);
        frame.setPreferredSize(new Dimension(450, 200));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
