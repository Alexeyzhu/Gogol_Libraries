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

public class TestCase10 {
    public static void main(String[] args) throws SQLException, InstanceAlreadyExistsException {
        GenerateDocumentTables documentTables = new GenerateDocumentTables();
        GenerateUserTables userTables = new GenerateUserTables();
        DropAllTables dropAllTables = new DropAllTables();
        dropAllTables.dropAllTables();
        documentTables.createDocumentTables();
        documentTables.fillDocumentTables();
        userTables.createUserTable();

        Patrons p = new Patrons();
        Librarians lib = new Librarians();

        Booking.checkOut(p.getID("i.mazan","3333333"),2, Documents.getDocType(2));
        lib.getBookedDoc();
        Booking.checkOut(p.getID("i.mazan","3333333"),1, Documents.getDocType(1));
    }
}
