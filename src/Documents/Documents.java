package Documents;

import DataBase.DBConnection;

import java.sql.SQLException;
import DataBase.DBConnection;
import org.omg.CORBA.OBJECT_NOT_EXIST;

import java.lang.invoke.WrongMethodTypeException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Documents {
    final static String BOOK = "Book";
    final static String JOURNAL = "Journal";
    final static String AUDIO_VIDEO_MATERIALS = "AV";
    final static int DOES_NOT_EXIST = 0;

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
     * sample of output (BOOK,JOURNAL,Documents.AV)
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
            journal = resultSet.getBoolean("id_journals");
            av = resultSet.getBoolean("id_av");
        }

        if (book) {
            return BOOK;
        } else if (journal) {
            return JOURNAL;
        } else if (av) {
            return AUDIO_VIDEO_MATERIALS;
        } else {
            throw new NullPointerException(" the library does not have book with id " + idDoc);
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
    public static void setCanCheckout(int idDoc, int canCheckOut) throws SQLException {
        statement.executeUpdate("UPDATE documents " +
                "SET canCheckout = '" + 0 +
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
      //  System.out.println(idDoc + " IDDOC");
        boolean isRef = false;
        resultSet = statement.executeQuery("SELECT * FROM documents " +
                "WHERE id = '" + idDoc + "'");
        while (resultSet.next()) {
            isRef = resultSet.getBoolean("isReference");
           // System.out.println("This document is Reference : " + isRef);
        }
        return isRef;
    }

    public static int[] getAllDocId(int id, String type) throws SQLException{
       // System.out.println("Type " + type + " id : " + id);
        ArrayList<Integer> idDocArrayList = new ArrayList<>();

        resultSet =switchResultSet(id, type);
        while (resultSet.next()){
            idDocArrayList.add(resultSet.getInt("id"));
        }

        if (idDocArrayList.isEmpty()){
            throw new OBJECT_NOT_EXIST("Document with this id doesn't exist");
        }

        int[] idDocArray = new int[idDocArrayList.size()];
        for (int i = 0; i < idDocArray.length; i++) {
            idDocArray[i] = idDocArrayList.get(i);
        }
        return idDocArray;
    }

    public static int getDocId(int id, String type) throws SQLException{
      //  System.out.println("Type " + type + " id : " + id);
        int idDoc = DOES_NOT_EXIST;

        resultSet = switchResultSet(id, type);
        if (resultSet.next()){
            idDoc = resultSet.getInt("id");
        }

        if (idDoc == DOES_NOT_EXIST){
            throw new OBJECT_NOT_EXIST("Document with this id doesn't exist");
        }

        return idDoc;
    }

    private static ResultSet switchResultSet (int id, String type) throws SQLException {
        switch (type) {
            case Documents.BOOK :
                return statement.executeQuery("SELECT * FROM documents " + "WHERE id_book = '" + id + "'");
            case Documents.JOURNAL :
                return statement.executeQuery("SELECT * FROM documents " + "WHERE id_journal = '" + id + "'");
            case Documents.AUDIO_VIDEO_MATERIALS :
                return statement.executeQuery("SELECT * FROM documents " + "WHERE id_av = '" + id + "'");
            default:
                throw new WrongMethodTypeException("Type with id : " + id + " not a \"Documents.Book\", " +
                        "\"Journal\" or \"Documents.AV materials\"");
        }
    }


}
