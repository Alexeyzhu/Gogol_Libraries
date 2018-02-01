import javax.management.InstanceAlreadyExistsException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstanceAlreadyExistsException {
        Documents documents = new Documents();
        System.out.println(documents.getDocType(2));
        Booking booking = new Booking();
        booking.checkOut(1, 2);
        Users users = new Users();
        Librarians librarians = new Librarians();
        librarians.addPerson("Dan", "Zhuchkov", "Innopolis", 123456789, "L");
        System.out.println(users.checkLoginPassword("answer : " + "n.zhuchkov", "305155464"));
        System.out.println(users.getType("a.zhuchkov", "305129464"));


        LogIn logIn = new LogIn();
        logIn.setVisible(true);
    }


}
