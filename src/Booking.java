import sun.plugin.dom.exception.WrongDocumentException;

import javax.swing.text.Document;
import java.lang.invoke.WrongMethodTypeException;
import java.sql.*;
import java.util.Date;

public class Booking {
    private final static long FOUR_WEEKS_IN_SEC = 2419200;
    private final static long THREE_WEEKS_IN_SEC = 1814400;
    private final static long TWO_WEEKS_IN_SEC = 1209600;
    private final static int CONVERT_SEC_IN_MILLISEC = 1000;
    private final static int ALL_CHECK_OUT = 0;

    private static Statement statement;
    private static ResultSet resultSet;

    // Create statement
    static {
        try {
            statement = new DBConnection().setConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    Booking() throws SQLException {
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
        // checks if this book is available
        idDoc = chooseDocumentObject(idDoc);
        if (idDoc == ALL_CHECK_OUT) {
            throw new NullPointerException("There is no such book or all of them are checked out");
        }

        boolean isReferenceBook = Documents.getDocType(idDoc).equals(Documents.BOOK) && Book.isReference(idDoc);
        if (Documents.canCheckOut(idDoc)) {
            if (isReferenceBook){
                throw new WrongDocumentException("You try check out reference book");
            }
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
        long additionalTime;

        if (Documents.getDocType(idDoc).equals(Documents.BOOK)) {
            if (Book.isBestSeller(Book.getBookID(idDoc))) {
                additionalTime = TWO_WEEKS_IN_SEC * CONVERT_SEC_IN_MILLISEC;
            } else if (type.equals("Faculty")) {
                additionalTime = FOUR_WEEKS_IN_SEC * CONVERT_SEC_IN_MILLISEC;
            } else {
                additionalTime = THREE_WEEKS_IN_SEC * CONVERT_SEC_IN_MILLISEC;
            }
        } else {
            additionalTime = TWO_WEEKS_IN_SEC * CONVERT_SEC_IN_MILLISEC;
        }

        Timestamp bookingDate = new Timestamp(new Date().getTime());
        Timestamp timeForReturn = new Timestamp(bookingDate.getTime() + additionalTime);
        statement.executeUpdate("INSERT INTO library.booking_sys (id_users, id_doc, checkout_time, returntime, isRenewed) " +
                "VALUES ('" + idUser + "','" + idDoc + "','" + bookingDate + "'," + timeForReturn + ",FALSE )");
    }

    public int chooseDocumentObject(int idDoc) throws SQLException {
        if (Documents.canCheckOut(idDoc)) {
            return idDoc;
        } else {
            int newIdDoc = ALL_CHECK_OUT;
            switch (Documents.getDocType(idDoc)) {
                case Documents.BOOK:
                    int idBook = Book.getBookID(idDoc);
                    System.out.println("Book id = " + idBook);

                    resultSet = statement.executeQuery("SELECT * FROM documents WHERE id_book = '" + idBook + "'");
                    while (resultSet.next()) {
                        newIdDoc = resultSet.getInt("id");
                    }
                    break;
                case Documents.JOURNAL:
                    int idJournal = JournalArt.getJournalID(idDoc);
                    System.out.println("Journal id = " + idJournal);

                    resultSet = statement.executeQuery("SELECT * FROM documents WHERE id_journal = '" + idJournal + "'");
                    while (resultSet.next()) {
                        newIdDoc = resultSet.getInt("id");
                    }
                    break;
                case Documents.AUDIO_VIDEO_MATERIALS:
                    int idAV = AV.getAVID(idDoc);
                    System.out.println("AV id = " + idAV);

                    resultSet = statement.executeQuery("SELECT * FROM documents WHERE id_book = '" + idAV + "'");
                    while (resultSet.next()) {
                        newIdDoc = resultSet.getInt("id");
                    }
                    break;
                default:
                    throw new WrongMethodTypeException("Document with this id " + idDoc + " not a \"Book\", " +
                            "\"Journal\" or \"AV materials\"");
            }

            System.out.println(Documents.getDocType(newIdDoc) + " id = " + newIdDoc + " can check out : " + Documents.canCheckOut(idDoc));
            if (Documents.canCheckOut(newIdDoc)) {
                return newIdDoc;
            }
        }
        return ALL_CHECK_OUT;
    }
}
