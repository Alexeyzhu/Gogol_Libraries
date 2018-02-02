import org.omg.CORBA.OBJECT_NOT_EXIST;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Documents {
    Statement statement;
    ResultSet resultSet;

    Documents() throws SQLException {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.setConnection();
        statement = connection.createStatement();
    }

    //TODO add document

    /**
     * Find out the type of the document
     * from database
     *
     * @param id_doc ID of document
     * @return type of input document
     * sample of output (BOOK,JOURNAL,AV)
     * @throws SQLException
     */
    public String getDocType(int id_doc) throws SQLException {
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
            return "BOOK";
        } else if (journal > 0) {
            return "JOURNAL";
        } else if (av > 0) {
            return "AV";
        } else {
            throw new NullPointerException("Something wrong in input or database");
        }
    }


    /**
     * Returns shelf of the document
     *
     * @param id_doc ID of document
     * @return shelf in String format
     * @throws SQLException
     */
    public String getShelf(int id_doc) throws SQLException {
        String shelf = "";
        resultSet = statement.executeQuery("SELECT * FROM documents " +
                "WHERE id = '" + id_doc + "'");
        while (resultSet.next()) {
            shelf = resultSet.getString("shelf");
            System.out.println(shelf);
        }
        return shelf;
    }

    /**
     * @param id_doc ID of document
     * @return true - if document can be checked out
     * otherwise false
     * @throws SQLException, OBJECT_NOT_EXIST
     */
    public boolean canCheckOut(int id_doc) throws SQLException, OBJECT_NOT_EXIST {
        boolean check = false;
        resultSet = statement.executeQuery("SELECT canCheckout FROM documents " +
                "WHERE id = '" + id_doc + "'");

        while (resultSet.next()) {
            check = resultSet.getBoolean("canCheckOut");
            if (!check)  new OBJECT_NOT_EXIST("Document with this ID = " + id_doc + " doesn't exist");
        }
        return check;
    }

    /**
     * Set parameter canCheckOut for document
     *
     * @param id_doc ID of document
     * @param canCheckOut
     * @throws SQLException
     */
    public void setCanCheckout(int id_doc, boolean canCheckOut) throws SQLException {
        statement.executeUpdate("UPDATE library.documents SET canCheckout = " + canCheckOut +
                " WHERE id = '" + id_doc + "'");
    }
}
