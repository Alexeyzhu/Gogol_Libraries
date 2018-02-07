package Tests;

import DataBase.DropAllTables;
import DataBase.GenerateDocumentTables;
import DataBase.GenerateUserTables;
import Documents.Booking;
import Documents.Documents;
import Users.Faculty;
import Users.Librarians;
import Users.Patrons;
import Users.Student;

import javax.management.InstanceAlreadyExistsException;
import java.sql.SQLException;

public class TestCase3 {

    public static void main(String[] args) throws SQLException, InstanceAlreadyExistsException {

        GenerateDocumentTables documentTables = new GenerateDocumentTables();
        GenerateUserTables userTables = new GenerateUserTables();
        DropAllTables dropAllTables = new DropAllTables();
        dropAllTables.dropAllTables();
        documentTables.createDocumentTables();
        documentTables.fillDocumentTables();
        userTables.createUserTable();

        Patrons f = new Faculty();
        Patrons s = new Student();
        Librarians l = new Librarians();

        Booking.checkOut(f.getID("f.galeev","1111111"),3, Documents.getDocType(3));
        l.getBookedDoc();

    }
}
