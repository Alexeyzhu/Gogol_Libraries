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

    public int getDocID(int id_doc) throws SQLException {
        int id_book = 0;
        resultSet = statement.executeQuery("SELECT id_book FROM documents " +
                "WHERE id ='" + id_doc + "'");
        while (resultSet.next()) {
            id_book = resultSet.getInt("id_book");
            System.out.println(id_book);
        }
        if (id_book == 0) {
            throw new NullPointerException("Something going wrong. This is not a book.");
        }
        return id_book;
    }

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
        }else {
            throw new NullPointerException("Something wrong in input or database");
        }
    }

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

    public boolean canCheckout(int id_doc) throws SQLException {
        boolean check = false;
        resultSet = statement.executeQuery("SELECT canCheckout FROM documents " +
                "WHERE id = '" + id_doc + "'");

        while (resultSet.next()) {
            check = resultSet.getBoolean("canCheckout");
            System.out.println(check);
        }
        return check;
    }

    public void setCanCheckout(int id_doc, boolean cancheckout) throws SQLException {
        statement.executeUpdate("UPDATE library.documents SET canCheckout = " + cancheckout +
                " WHERE id = '" + id_doc + "'");
    }
}
