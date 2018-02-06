import javax.management.InstanceAlreadyExistsException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstanceAlreadyExistsException {

        GenerateUserTables generateUserTables = new GenerateUserTables();
        GenerateDocumentTables generateDocumentTables = new GenerateDocumentTables();
        DropAllTables dropAllTables = new DropAllTables();
        generateDocumentTables.createDocumentTables();
        generateUserTables.createUserTable();
        dropAllTables.dropAllTables();

//        Booking booking = new Booking();
//        System.out.println(Documents.canCheckOut(3));
//        System.out.println("Document type = " + Documents.getDocType(2));
//        booking.checkOut(1, 2, "Student");
//        booking.checkOut(1, 1, "Student");
//        booking.checkOut(2, 2, "Faculty");
//        booking.checkOut(1, 2, "Student");
//        booking.checkOut(2, 5, "Faculty");
//        Users users = new Users();
//        Librarians librarians = new Librarians();
//        librarians.addPerson("Dan", "Zhuchkov", "Innopolis", "123456789", "L");
//        System.out.println(users.checkLoginPassword("answer : " + "n.zhuchkov", "305155464"));
//        System.out.println(users.getType("a.zhuchkov", "305129464"));
    }


}
