import DataBase.GenerateDocumentTables;
import DataBase.GenerateUserTables;

import javax.management.InstanceAlreadyExistsException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstanceAlreadyExistsException {

        GenerateUserTables generateUserTables = new GenerateUserTables();
        GenerateDocumentTables generateDocumentTables = new GenerateDocumentTables();
        DropAllTables dropAllTables = new DropAllTables();
//        generateDocumentTables.createDocumentTables();
//        generateUserTables.createUserTable();
//        dropAllTables.dropAllTables();
//        generateDocumentTables.fillDocumentTables();

//        Documents.Booking booking = new Documents.Booking();
//        System.out.println(Documents.Documents.canCheckOut(3));
//        System.out.println("Document type = " + Documents.Documents.getDocType(2));
//        booking.checkOut(1, 2, "Users.Student");
//        booking.checkOut(1, 1, "Users.Student");
//        booking.checkOut(2, 2, "Users.Faculty");
//        booking.checkOut(1, 2, "Users.Student");
//        booking.checkOut(2, 5, "Users.Faculty");
//        Users.Users users = new Users.Users();
//        Users.Librarians librarians = new Users.Librarians();
//        librarians.addPerson("Dan", "Zhuchkov", "Innopolis", "123456789", "L");
//        System.out.println(users.checkLoginPassword("answer : " + "n.zhuchkov", "305155464"));
//        System.out.println(users.getType("a.zhuchkov", "305129464"));
    }


}
