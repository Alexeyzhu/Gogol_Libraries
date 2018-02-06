import javax.swing.text.Document;
import java.lang.invoke.WrongMethodTypeException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Booking {
    final static long FOUR_WEEKS_IN_SEC = 2419200;
    final static long THREE_WEEKS_IN_SEC = 1814400;
    final static long TWO_WEEKS_IN_SEC = 1209600;
    final static int CONVERT_SEC_IN_MILLISEC = 1000;

    static Statement statement;
    static ResultSet resultSet;

    Booking() throws SQLException {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.setConnection();
        statement = connection.createStatement();
    }

    /**
     * Insert user's and document's ID into booking table and
     * system gives unique ID to this booking transaction
     *
     * @param idUser id of user from user table
     * @param idDoc  id of document from document table
     * @throws SQLException
     */
    public boolean checkOut(int idUser, int idDoc, String type) throws SQLException {
        Documents documents = new Documents();
        if (Documents.canCheckOut(idDoc)) {
            Documents.setCanCheckout(idDoc, false);
            addBooking(idUser, idDoc, type);
            return true;
        }
        return false;
    }

    /**
     * Insert user's and document's into to booking table
     *
     * @param idUser id of user from user table
     * @param idDoc  id of document from document table
     * @throws SQLException
     */
    private void addBooking(int idUser, int idDoc, String type) throws SQLException {
        Date date = new Date();
        Date dateForReturn = new Date();
        long additionalTime = 0;

        switch (type) {
            case "Patron":
                additionalTime = FOUR_WEEKS_IN_SEC * CONVERT_SEC_IN_MILLISEC;
                break;
            case "Student":
                additionalTime = THREE_WEEKS_IN_SEC * CONVERT_SEC_IN_MILLISEC;
                break;
            default:
                throw new WrongMethodTypeException("User isn't \"Faculty\" or \"Patron\"");
        }

        // Bestseller only for two week. For all Faculty
        if (Documents.getDocType(idDoc).equals(Documents.BOOK) && Book.isBestSeller(Book.getBookID(idDoc))) {
            additionalTime = TWO_WEEKS_IN_SEC * CONVERT_SEC_IN_MILLISEC;
        }

        dateForReturn.setTime(date.getTime() + additionalTime);

        java.sql.Timestamp bookingDate = new java.sql.Timestamp(date.getTime());
        java.sql.Timestamp timeForReturn = new java.sql.Timestamp(dateForReturn.getTime());
        statement.executeUpdate("INSERT INTO library.booking_sys (id_users, id_doc, checkout_time, returntime, isRenewed) " +
                "VALUES ('" + idUser + "','" + idDoc + "','" + bookingDate + "'," + timeForReturn + ",FALSE )");
    }

    public int iteratorDocuments(int idDoc) {
        return 0;
    }
}
