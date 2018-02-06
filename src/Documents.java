import org.omg.CORBA.OBJECT_NOT_EXIST;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Documents {
    final static String BOOK = "Book";
    final static String JOURNAL = "Journal";
    final static String AUDIO_VIDEO_MATERIALS = "Audio & Video materials";

    static ResultSet resultSet;
    static Statement statement;

    // Create statement
    static {
        try {
            statement = new DBConnection().setConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String[] getDocumentName(int idDoc) throws SQLException {
        String type = getDocType(idDoc);
        switch (type) {
            case BOOK:
                return Book.getBookName(idDoc);
            case JOURNAL:
                return JournalArt.getJournalName(idDoc);
            case AUDIO_VIDEO_MATERIALS:
                return AV.getAVName(idDoc);
            default:
                throw new NumberFormatException();
        }
    }

    /**
     * Find out the type of the document
     * from database
     *
     * @param idDoc ID of document
     * @return type of input document
     * sample of output (BOOK,JOURNAL,AV)
     * @throws SQLException
     */
    public static String getDocType(int idDoc) throws SQLException {
        boolean book = false;
        boolean journal = false;
        boolean av = false;

        // check implementation. Change 'id_book', 'id_journal', 'id_av' to camelCase or transform they to constants
        resultSet = statement.executeQuery("SELECT * FROM documents WHERE id = '" + idDoc + "'");
        while (resultSet.next()) {
            book = resultSet.getBoolean("id_book");
            journal = resultSet.getBoolean("id_journal");
            av = resultSet.getBoolean("id_av");
        }

        if (book) {
            return BOOK;
        } else if (journal) {
            return JOURNAL;
        } else if (av) {
            return AUDIO_VIDEO_MATERIALS;
        } else {
            throw new NullPointerException("Something wrong in input or database");
        }
    }


    /**
     * Returns shelf of the document
     *
     * @param idDoc ID of document
     * @return shelf in String format
     * @throws SQLException
     */
    public static String getShelf(int idDoc) throws SQLException {
        String shelf = "";
        resultSet = statement.executeQuery("SELECT * FROM documents " +
                "WHERE id = '" + idDoc + "'");

        // can you transform 'shelf' to constant?
        // for this idDoc we always have only one shelf? If no, chat me @HardLight
        while (resultSet.next()) {
            shelf = resultSet.getString("shelf");
            System.out.println("Shelf : " + shelf);
        }

        return shelf;
    }

    /**
     * @param idDoc ID of document
     * @return true - if document can be checked out
     * otherwise false
     * @throws SQLException, OBJECT_NOT_EXIST
     */
    public static boolean canCheckOut(int idDoc) throws SQLException, OBJECT_NOT_EXIST {
        boolean check = false;
        resultSet = statement.executeQuery("SELECT * FROM documents " +
                "WHERE id = '" + idDoc + "'");

        while (resultSet.next()) {
            check = resultSet.getBoolean("canCheckOut");
          //  if (!check) throw new OBJECT_NOT_EXIST("Document with this ID = " + idDoc + " doesn't exist");
        }
        return check;
    }

    /**
     * Set parameter canCheckOut for document
     *
     * @param idDoc       ID of document
     * @param canCheckOut
     * @throws SQLException
     */
    public static void setCanCheckout(int idDoc, boolean canCheckOut) throws SQLException {
        statement.executeUpdate("UPDATE documents " +
                "SET canCheckout = '" + canCheckOut +
                "' WHERE id = '" + idDoc + "'");
    }

    /**
     * Query:
     * true - if book is Reference book
     * false - otherwise
     *
     * @param idDoc id of the book from the books table
     * @return true - if book is Reference book
     * false - otherwise
     * @throws SQLException
     */
    public static boolean isReference(int idDoc) throws SQLException {
        boolean isRef = false;
        resultSet = statement.executeQuery("SELECT isReference FROM documents " +
                "WHERE id = '" + idDoc + "'");
        while (resultSet.next()) {
            isRef = resultSet.getBoolean("isReference");
            System.out.println("This book is Reference book - " + isRef);
        }
        return isRef;
    }
}
