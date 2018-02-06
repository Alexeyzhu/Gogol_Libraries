package Tests;


import Documents.*;
import Users.*;

import javax.management.InstanceAlreadyExistsException;
import java.sql.SQLException;

public class TestCase1 {

    public static void main(String[] args) throws SQLException, InstanceAlreadyExistsException {
        Librarians lib = new Librarians();
        Documents documents = new Documents();
        Patrons patrons = new Patrons();
        Booking booking = new Booking();

        booking.checkOut(patrons.getID("f.galeev","45640902"),1, Documents.getDocType(1));
//        lib.getBookedDoc();
    }
}