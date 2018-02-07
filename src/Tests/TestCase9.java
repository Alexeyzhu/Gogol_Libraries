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

public class TestCase9 {
    public static void main(String[] args) throws SQLException, InstanceAlreadyExistsException {
        GenerateDocumentTables documentTables = new GenerateDocumentTables();
        GenerateUserTables userTables = new GenerateUserTables();
        DropAllTables dropAllTables = new DropAllTables();
        dropAllTables.dropAllTables();
        documentTables.createDocumentTables();
        documentTables.fillDocumentTables();
        userTables.createUserTable();

        Patrons s = new Student();
        Patrons f = new Faculty();
        Librarians lib = new Librarians();
        Booking booking = new Booking();
        Documents documents = new Documents();

        booking.checkOut(s.getID("i.mazan","3333333"),2,documents.getDocType(2));
        lib.getBookedDoc();
    }
}
