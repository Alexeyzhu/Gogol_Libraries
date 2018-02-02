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
     * @param shelf where will be book
     * @param canCheckout false for special books
     * @throws SQLException
     */
    public void addBook(String name, String author, String publisher,
                        String edition, String editionYear,
                        boolean isBestSeller, String shelf,
                        boolean canCheckout) throws SQLException {
        int id_book = 0;
        statement.executeUpdate("INSERT INTO books (name, author, publisher, edition, edition_year, isBestSeller) " +
                "VALUES ('" + name + "','" + author + "','" + publisher + "'," +
                "'" + edition + "','" + editionYear + "'," + isBestSeller + ") ");
        resultSet = statement.executeQuery("SELECT id FROM books " +
                "WHERE name = '" + name + "' AND author = '" + author + "' AND publisher = '" + publisher + "'" +
                " AND edition = '" + edition + "' AND edition_year = '" + editionYear + "' ");

        while (resultSet.next()){
            id_book = resultSet.getInt("id");
        }

        statement.executeUpdate("INSERT INTO documents (id_book,shelf, canCheckout)" +
                "VALUES (" + id_book + ",'" + shelf + "'," + canCheckout + ")");

    }

    /**
     * Returns book's ID from book table according to its ID
     * in document table
     *
     * @param id_doc ID of book from the document table
     * @return book's ID from book table
     * @throws SQLException
     */
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

    /**
     * Query:
     * true - if book is Bestseller
     * false - otherwise
     *
     * @param id_book id of the book from the books table
     * @return true - if book is Bestseller
     * false - otherwise
     * @throws SQLException
     */
    public boolean isBestSeller(int id_book) throws SQLException {
        boolean isBest = false;
        resultSet = statement.executeQuery("SELECT isBestSeller FROM library.books " +
                "WHERE id = '" + id_book + "'");
        while (resultSet.next()) {
            isBest = resultSet.getBoolean("isBestSeller");
            System.out.println(isBest);
        }
        return isBest;
    }
}
