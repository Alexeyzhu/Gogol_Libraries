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
    public void checkOut(int idUser, int idDoc, String type) throws SQLException {
        // checks if this book is available
        idDoc = chooseDocumentObject(idDoc);
        if (idDoc == 0) {
            throw new NullPointerException("There is no such book or all of them are checked out");
        }
        if (Documents.canCheckOut(idDoc)) {
            Documents.setCanCheckout(idDoc, false);
            addBooking(idUser, idDoc, type);
        }
    }

    /**
     * Insert user's and document's into to booking table
     *
     * @param id_us  id of user from user table
     * @param id_doc id of document from document table
     * @throws SQLException
     */
    private void addBooking(int id_us, int id_doc, String type) throws SQLException {
        Date date = new Date();
        Date dateForReturn = new Date();
        long additionalTime = 0;
        Book book = new Book();

        switch (type) {
            case "Faculty":
                additionalTime = FOUR_WEEKS_IN_SEC * CONVERT_SEC_IN_MILLISEC;
                break;
            case "Student":
                if (Documents.getDocType(id_doc).equals("BOOK") && book.isBestSeller(id_doc)) {
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
        statement.executeUpdate("INSERT INTO booking_sys (id_users, id_doc, checkout_time, returntime) " +
                "VALUES ('" + id_us + "','" + id_doc + "','" + bookingDate + "','" + timeForReturn + "')");
    }

    public int chooseDocumentObject(int idDoc) throws SQLException {
        if (Documents.canCheckOut(idDoc)) {
            return idDoc;
        } else {
            int idBook = Book.getBookID(idDoc);
            System.out.println(idBook + " BookID");
            resultSet = statement.executeQuery("SELECT * FROM documents WHERE id_book = '" + idBook + "'");
            while (resultSet.next()) {
                idDoc = resultSet.getInt("id");
                System.out.println(idDoc + " DocID");
                System.out.println(Documents.canCheckOut(idDoc) + " " + idDoc);
                if (Documents.canCheckOut(idDoc)) {

                    return idDoc;
                }
            }
        }
        return 0;
    }
}
