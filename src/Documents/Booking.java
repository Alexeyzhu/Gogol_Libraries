package Documents;

import DataBase.DBConnection;
import Users.Users;
import com.sun.xml.internal.bind.v2.TODO;
import sun.plugin.dom.exception.WrongDocumentException;

import javax.management.InstanceAlreadyExistsException;
import java.lang.invoke.WrongMethodTypeException;
import java.sql.*;
import java.util.Date;

public class Booking {
    private final static long FOUR_WEEKS_IN_MILLISECONDS = 2419200000L;
    private final static long THREE_WEEKS_IN_MILLISECONDS = 1814400000L;
    private final static long TWO_WEEKS_IN_MILLISECONDS = 1209600000L;
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

    /**
     * Insert user's and document's ID into booking table and
     * system gives unique ID to this booking transaction
     *
     * @param idUser id of user from user table
     * @param idDoc  id of document from document table
     * @throws SQLException
     */
    public static boolean checkOut(int idUser, int idDoc, String type) throws SQLException, InstanceAlreadyExistsException {
        // checks if this book is available
        idDoc = chooseDocumentObject(idDoc);

        if (idDoc == ALL_CHECK_OUT) {
//            TODO return exception
            //throw new NullPointerException("There is no such book or all of them are checked out");
            System.out.println("There is no such book or all of them are checked out");
            System.out.println();
        }

        if (Documents.canCheckOut(idDoc)) {
            if (Documents.isReference(idDoc)){
                throw new WrongDocumentException("You try check out reference book");
            }
            Documents.setCanCheckout(idDoc, 0);
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
    private static void addBooking(int idUser, int idDoc, String type) throws SQLException, InstanceAlreadyExistsException {
        if (isAlreadyHas(idUser, idDoc)){
            throw new InstanceAlreadyExistsException("Patron already check out this document");
        }

        long additionalTime;
        if (Documents.getDocType(idDoc).equals(Documents.BOOK)) {
            if (Book.isBestSeller(Book.getBookID(idDoc))) {
                additionalTime = TWO_WEEKS_IN_MILLISECONDS;
            } else if (new Users().getStatus(idUser).equals("Faculty")) {
                additionalTime = FOUR_WEEKS_IN_MILLISECONDS;
            } else {
                additionalTime = THREE_WEEKS_IN_MILLISECONDS;
            }
        } else {
            additionalTime = TWO_WEEKS_IN_MILLISECONDS;
        }

        Timestamp bookingDate = new Timestamp(new Date().getTime());
        Timestamp timeForReturn = new Timestamp(bookingDate.getTime() + additionalTime);
        statement.executeUpdate("INSERT INTO booking_sys (id_users, id_doc, checkout_time, returntime, isRenewed) " +
                "VALUES ('" + idUser + "','" + idDoc + "','" + bookingDate + "','" + timeForReturn + "', 0 )");
    }

    /**
     * check is patron already has this document or it copy
     *
     * @param idUser - patron id
     * @param idDoc - document id
     * @return true if patron already has this document or it copy
     *      otherwise false
     * @throws SQLException
     */
    private static boolean isAlreadyHas(int idUser, int idDoc) throws SQLException {
        resultSet = statement.executeQuery("SELECT * FROM booking_sys WHERE id_users = '" + idUser + "'");
        while (resultSet.next()){
            int bookedIdDoc = resultSet.getInt("id_doc");

            if (idDoc == bookedIdDoc){
                return true;
            }

            String newDocType = Documents.getDocType(idDoc);
            String bookedDocType = Documents.getDocType(bookedIdDoc);

            if (newDocType.equals(bookedDocType)) {
                switch (Documents.getDocType(idDoc)) {
                    case Documents.BOOK:
                        if (Book.getBookID(idDoc) == Book.getBookID(bookedIdDoc)) return true;
                        break;
                    case Documents.JOURNAL:
                        if (JournalArt.getJournalID(idDoc) == JournalArt.getJournalID(bookedIdDoc)) return true;
                        break;
                    case Documents.AUDIO_VIDEO_MATERIALS:
                        if (AV.getAVID(idDoc) == AV.getAVID(bookedIdDoc)) return true;
                        break;
                    default:
                        throw new WrongMethodTypeException("Document with this id " + idDoc + " not a \"Documents.Book\", " +
                                "\"Journal\" or \"Documents.AV materials\"");
                }
            }
        }
        return false;
    }

    /**
     * return acceptable id document identical input id document or ALL_CHECK_OUT if all identical document already check out
     *
     * @param idDoc document id
     * @return acceptable id document
     * @throws SQLException
     */
    private static int chooseDocumentObject(int idDoc) throws SQLException {
        if (Documents.canCheckOut(idDoc)) {
            return idDoc;
        } else {
            int idSearch;
            switch (Documents.getDocType(idDoc)) {
                case Documents.BOOK:
                    idSearch = Book.getBookID(idDoc);
                    resultSet = statement.executeQuery("SELECT * FROM documents WHERE id_book = '" + idSearch + "'");
                    break;
                case Documents.JOURNAL:
                    idSearch = JournalArt.getJournalID(idDoc);
                    resultSet = statement.executeQuery("SELECT * FROM documents WHERE id_journal = '" + idSearch + "'");
                    break;
                case Documents.AUDIO_VIDEO_MATERIALS:
                    idSearch = AV.getAVID(idDoc);
                    resultSet = statement.executeQuery("SELECT * FROM documents WHERE id_av = '" + idSearch + "'");
                    break;
                default:
                    throw new WrongMethodTypeException("Document with this id " + idDoc + " not a \"Documents.Book\", " +
                            "\"Journal\" or \"Documents.AV materials\"");
            }

            int newIdDoc;
            while (resultSet.next()) {
                newIdDoc = resultSet.getInt("id");
                if (Documents.canCheckOut(newIdDoc)){
                    statement.close();
                    return newIdDoc;
                }
            }

        }
        return ALL_CHECK_OUT;
    }
}
