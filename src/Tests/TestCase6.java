package Tests;

import DataBase.DropAllTables;
import DataBase.GenerateDocumentTables;
import DataBase.GenerateUserTables;
import Documents.Booking;
import Documents.Documents;
import Users.Librarians;
import Users.Patrons;

import javax.management.InstanceAlreadyExistsException;
import java.sql.SQLException;

public class TestCase6 {
    public static void main(String[] args) throws SQLException, InstanceAlreadyExistsException {
        GenerateDocumentTables documentTables = new GenerateDocumentTables();
        GenerateUserTables userTables = new GenerateUserTables();
        DropAllTables dropAllTables = new DropAllTables();
        dropAllTables.dropAllTables();
        documentTables.createDocumentTables();
        documentTables.fillDocumentTables();
        userTables.createUserTable();

        Patrons p1 = new Patrons();
        Librarians lib = new Librarians();
        Booking booking = new Booking();
        Documents documents = new Documents();

        booking.checkOut(p1.getID("f.galeev","1111111"),3,documents.getDocType(3));
        lib.getBookedDoc();
        booking.checkOut(p1.getID("f.galeev","1111111"),3,documents.getDocType(3));
    }
}
