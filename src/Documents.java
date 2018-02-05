import org.omg.CORBA.OBJECT_NOT_EXIST;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Documents {
    final static String BOOK = "Book";
    final static String JOURNAL = "Journal";
    final  static String AUDIO_VIDEO_MATERIALS = "Audio & Video materials";

    static ResultSet resultSet;
    static Statement statement;

    static {
        try {
            statement = new DBConnection().setConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    Documents() throws SQLException {
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
     * @param id_doc ID of document
     * @return type of input document
     * sample of output (BOOK,JOURNAL,AV)
     * @throws SQLException
     */
    public static String getDocType(int id_doc) throws SQLException {
        int book = 0;
        int journal = 0;
        int av = 0;
        resultSet = statement.executeQuery("SELECT * FROM documents WHERE id = '" + id_doc + "'");
        while (resultSet.next()) {
            book = resultSet.getInt("id_book");
            journal = resultSet.getInt("id_journal");
            av = resultSet.getInt("id_av");

        }
        if (book > 0) {
            return BOOK;
        } else if (journal > 0) {
            return JOURNAL;
        } else if (av > 0) {
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
        while (resultSet.next()) {
            shelf = resultSet.getString("shelf");
            System.out.println(shelf);
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
        resultSet = statement.executeQuery("SELECT canCheckout FROM documents " +
                "WHERE id = '" + idDoc + "'");

        while (resultSet.next()) {
            check = resultSet.getBoolean("canCheckOut");
            if (!check)  new OBJECT_NOT_EXIST("Document with this ID = " + idDoc + " doesn't exist");
        }
        return check;
    }

    /**
     * Set parameter canCheckOut for document
     *
     * @param idDoc ID of document
     * @param canCheckOut
     * @throws SQLException
     */
    public static void setCanCheckout(int idDoc, boolean canCheckOut) throws SQLException {
        statement.executeUpdate("UPDATE library.documents SET canCheckout = " + canCheckOut +
                " WHERE id = '" + idDoc + "'");
    }
}
