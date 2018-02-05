import java.sql.ResultSet;
import java.sql.SQLException;

public class Book extends Documents {
    Book() throws SQLException {
    }

    /**
     * Add book to the books table
     *
     * @param name
     * @param author
     * @param publisher
     * @param edition
     * @param editionYear
     * @param isBestSeller
     * @param shelf        where will be book
     * @param canCheckout  false for special books
     * @throws SQLException
     */
    public static void addBook(String name, String author, String publisher,
                        String edition, String editionYear,
                        boolean isBestSeller, String shelf,
                        boolean canCheckout, boolean isReference) throws SQLException {
        int id_book = 0;
        statement.executeUpdate("INSERT INTO books (name, author, publisher, edition, edition_year, isBestSeller) " +
                "VALUES ('" + name + "','" + author + "','" + publisher + "'," +
                "'" + edition + "','" + editionYear + "'," + isBestSeller + ") ");
        resultSet = statement.executeQuery("SELECT id FROM books " +
                "WHERE name = '" + name + "' AND author = '" + author + "' AND publisher = '" + publisher + "'" +
                " AND edition = '" + edition + "' AND edition_year = '" + editionYear + "' ");

        while (resultSet.next()) {
            id_book = resultSet.getInt("id");
        }

        statement.executeUpdate("INSERT INTO documents (id_book,shelf, canCheckout)" +
                "VALUES (" + id_book + ",'" + shelf + "'," + canCheckout + ")");

    }

    /**
     * Returns book's ID from book table according to its ID
     * in document table
     *
     * @param idDoc ID of book from the document table
     * @return book's ID from book table
     * @throws SQLException
     */
    public static int getBookID(int idDoc) throws SQLException {
        int id_book = 0;
        resultSet = statement.executeQuery("SELECT id_book FROM documents " +
                "WHERE id ='" + idDoc + "'");
        while (resultSet.next()) {
            id_book = resultSet.getInt("id_book");
            System.out.println(id_book);
        }
        if (id_book == 0) {
            throw new NullPointerException("Something going wrong. This is not a book.");
        }
        return id_book;
    }

    /**
     *
     * @param idDoc
     * @return
     * @throws SQLException
     */
    public static String[] getBookName(int idDoc) throws SQLException {
        int idBook = getBookID(idDoc);
        String[] name = new String[2];
        resultSet = statement.executeQuery("SELECT * FROM books WHERE id = '" + idBook + "'");
        while (resultSet.next()){
            name[0] = resultSet.getNString("name");
            name[1] = resultSet.getNString("author");
        }
        return name;
    }

    /**
     * Query:
     * true - if book is Bestseller
     * false - otherwise
     *
     * @param idBook id of the book from the books table
     * @return true - if book is Bestseller
     * false - otherwise
     * @throws SQLException
     */
    public static boolean isBestSeller(int idBook) throws SQLException {
        boolean isBest = false;
        resultSet = statement.executeQuery("SELECT isBestSeller FROM library.books " +
                "WHERE id = '" + idBook + "'");
        while (resultSet.next()) {
            isBest = resultSet.getBoolean("isBestSeller");
            System.out.println(isBest);
        }
        return isBest;
    }
}
