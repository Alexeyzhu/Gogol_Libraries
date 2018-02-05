import java.lang.invoke.WrongMethodTypeException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Booking {
    final static long FOUR_WEEKS_IN_SEC  = 2419200;
    final static long THREE_WEEKS_IN_SEC = 1814400;
    final static long TWO_WEEKS_IN_SEC   = 1209600;
    final static int CONVERT_SEC_IN_MILLISEC = 1000;
    Statement statement;
    ResultSet resultSet;

    Booking() throws SQLException {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.setConnection();
        statement = connection.createStatement();
    }

    /**
     * Insert user's and document's ID into booking table and
     * system gives unique ID to this booking transaction
     *
     * @param id_us id of user from user table
     * @param id_doc id of document from document table
     * @throws SQLException
     */
    public void checkOut(int id_us, int id_doc, String type) throws SQLException {
        Documents documents = new Documents();
        if (documents.canCheckOut(id_doc)) {
            documents.setCanCheckout(id_doc, false);
            addBooking(id_us, id_doc, type);
        }
    }

    /**
     * Insert user's and document's into to booking table
     *
     * @param id_us id of user from user table
     * @param id_doc id of document from document table
     * @throws SQLException
     */
    private void addBooking(int id_us, int id_doc, String type) throws SQLException {
        Date date = new Date();
        Date dateForReturn = new Date();
        long additionalTime = 0;
        Documents doc = new Documents();
        Book book = new Book();

        switch (type) {
            case "Patron":
                additionalTime = FOUR_WEEKS_IN_SEC * CONVERT_SEC_IN_MILLISEC;
                break;
            case "Student":
                if (doc.getDocType(id_doc).equals("BOOK") && book.isBestSeller(id_doc)) {
                    additionalTime = THREE_WEEKS_IN_SEC * CONVERT_SEC_IN_MILLISEC;
                } else {
                    additionalTime = TWO_WEEKS_IN_SEC * CONVERT_SEC_IN_MILLISEC;
                }
                break;
            default:
                throw new WrongMethodTypeException("User isn't \"Faculty\" or \"Patron\"");
        }
        dateForReturn.setTime(date.getTime() + additionalTime);

        java.sql.Timestamp bookingDate = new java.sql.Timestamp(date.getTime());
        java.sql.Timestamp timeForReturn = new java.sql.Timestamp(dateForReturn.getTime());
        statement.executeUpdate("INSERT INTO library.booking_sys (id_users, id_doc, checkout_time, isRenewed) " +
                "VALUES ('" + id_us + "','" + id_doc + "','" + bookingDate + "',FALSE )");
    }
}
