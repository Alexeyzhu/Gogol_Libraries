package Documents;

import javax.management.InstanceAlreadyExistsException;
import java.sql.SQLException;

public class Book extends Documents {

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
                               boolean canCheckout, boolean isReference) throws SQLException, InstanceAlreadyExistsException {

        // Get exception if we already have this book on this shelf
        resultSet = statement.executeQuery("SELECT shelf FROM library.books WHERE name = '" + name +
                "' AND author = '" + author + "' AND publisher = '" + publisher + "' AND edition = '" + edition +
                "' AND edition_year = '" + editionYear + "' AND isBestseller = " + isBestSeller +
                " AND isReference = " + isReference + "");
        while (resultSet.next()) {
            if (resultSet.getNString("shelf").equals(shelf)) {
                throw new InstanceAlreadyExistsException("This book already on this shelf");
            }
        }

        int idBook = 0;
        statement.executeUpdate("INSERT INTO books (name, author, publisher, edition, edition_year, isBestSeller, isReference) " +
                "VALUES ('" + name + "','" + author + "','" + publisher + "'," +
                "'" + edition + "','" + editionYear + "'," + isBestSeller + "','" + isReference + ") ");
        resultSet = statement.executeQuery("SELECT id FROM books " +
                "WHERE name = '" + name + "' AND author = '" + author + "' AND publisher = '" + publisher + "'" +
                " AND edition = '" + edition + "' AND edition_year = '" + editionYear + "' ");

        while (resultSet.next()) {
            idBook = resultSet.getInt("id");
        }

        statement.executeUpdate("INSERT INTO documents (id_book,shelf, canCheckout)" +
                "VALUES (" + idBook + ",'" + shelf + "'," + canCheckout + ")");

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
        final int BOOK_DOES_NOT_EXIST = 0;

        int id_book = BOOK_DOES_NOT_EXIST;
        resultSet = statement.executeQuery("SELECT * FROM documents " +
                "WHERE id ='" + idDoc + "'");

        // can you transform 'id_book' to constant?
        while (resultSet.next()) {
            id_book = resultSet.getInt("id_book");
           // System.out.println("Documents.Book id = " + id_book);

           // System.out.println("Documents.Book id = " + id_book);

        }

        if (id_book == BOOK_DOES_NOT_EXIST) {
            throw new NullPointerException("Something going wrong. This is not a book.");
        }

        return id_book;
    }

    /**
     * @param idDoc
     * @return
     * @throws SQLException
     */
    public static String[] getBookName(int idDoc) throws SQLException {
        int idBook = getBookID(idDoc);
        String[] name = new String[2];
        resultSet = statement.executeQuery("SELECT * FROM books WHERE id = '" + idBook + "'");
        while (resultSet.next()) {
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
     * @param idDoc id of the book from the documents table
     * @return true - if book is Bestseller
     * false - otherwise
     * @throws SQLException
     */
    public static boolean isBestSeller(int idDoc) throws SQLException {
        boolean isBest = false;
        String type = Documents.getDocType(idDoc);
        // check if it really a book or not
        if (type.equals("Book")) {
            int idBook = getBookID(idDoc);
            resultSet = statement.executeQuery("SELECT isBestSeller FROM books " +
                    "WHERE id = '" + idBook + "'");
            while (resultSet.next()) {
                isBest = resultSet.getBoolean("isBestSeller");
              //  System.out.println("This book is BestSeller - " + isBest);
            }
            return isBest;
        } else {
            return false;
        }
    }
}
