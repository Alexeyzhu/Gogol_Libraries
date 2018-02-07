package Tests;

import DataBase.DropAllTables;
import DataBase.GenerateDocumentTables;
import DataBase.GenerateUserTables;
import Documents.*;
import Users.*;

import javax.management.InstanceAlreadyExistsException;
import java.sql.SQLException;

public class TestCase2 {
    public static void main(String[] args) throws SQLException, InstanceAlreadyExistsException {

        GenerateDocumentTables documentTables = new GenerateDocumentTables();
        GenerateUserTables userTables = new GenerateUserTables();
        DropAllTables dropAllTables = new DropAllTables();
        dropAllTables.dropAllTables();
        documentTables.createDocumentTables();
        documentTables.fillDocumentTables();
        userTables.createUserTable();

        Librarians lib = new Librarians();
        Documents documents = new Documents();
        Patrons patrons = new Patrons();
        Booking booking = new Booking();

        booking.checkOut(patrons.getID("f.galeev","1111111"),15, documents.getDocType(15));


    }
}
