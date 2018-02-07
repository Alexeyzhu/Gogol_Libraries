package DataBase;

import java.sql.SQLException;
import java.sql.Statement;

public class GenerateDocumentTables {
    private static Statement statement;

    static {
        try {
            statement = new DBConnection().setConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createDocumentTables() throws SQLException {

        statement.executeUpdate("CREATE TABLE av " +
                "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title VARCHAR (50)," +
                "author VARCHAR (50)" +
                ")");
        statement.executeUpdate("CREATE TABLE journals " +
                "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title VARCHAR (50)," +
                "author VARCHAR (50)," +
                "journal_name VARCHAR (50)," +
                "issue VARCHAR (50)," +
                "editor VARCHAR (50)" +
                ")");
        statement.executeUpdate("CREATE TABLE books " +
                "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR (50)," +
                "author VARCHAR (50)," +
                "publisher VARCHAR (50)," +
                "edition INTEGER," +
                "edition_year INTEGER," +
                "isBestSeller TINYINT DEFAULT 0" +
                ")");
        statement.executeUpdate("CREATE TABLE documents " +
                "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_book INTEGER ," +
                "id_journals INTEGER ," +
                "id_av INTEGER ," +
                "shelf VARCHAR (10) ," +
                "canCheckout TINYINT DEFAULT 1," +
                "isReference TINYINT DEFAULT 0," +
                "FOREIGN KEY (id_book) REFERENCES books(id)," +
                "FOREIGN KEY (id_journals) REFERENCES journal_articles(id)," +
                "FOREIGN KEY (id_av) REFERENCES av(id)" +
                ")");
        statement.executeUpdate("CREATE TABLE booking_sys " +
                "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_users INTEGER," +
                "id_doc INTEGER," +
                "checkout_time DATE," +
                "returnTime DATE," +
                "isRenewed TINYINT DEFAULT 0" +
                ")");

    }

    public void fillDocumentTables() throws SQLException {
        statement.executeUpdate("INSERT INTO books (name, author, publisher, edition, edition_year, isBestSeller) " +
                "VALUES('Book','Rudyard Kipling','AST', 3, 2008, 0) ");
        statement.executeUpdate("INSERT INTO books (name, author, publisher, edition, edition_year, isBestSeller) " +
                "VALUES('Harry Potter','Joan Rowling', 'Eksmo', 2, 2015, 1) ");
        statement.executeUpdate("INSERT INTO books (name, author, publisher, edition, edition_year, isBestSeller) " +
                "VALUES('Alice_s Adventures in Wonderland','Lewis Carrol','AST press', 6, 2016, 0) ");
        statement.executeUpdate("INSERT INTO av (title, author) " +
                "VALUES ('New world','Maksim')");
        statement.executeUpdate("INSERT INTO journals (title, author, journal_name, issue, editor) " +
                "VALUES('Life on the Mars','John Mars', 'Scientist press', '12', 'Marcus') ");
        statement.executeUpdate("INSERT INTO documents (id_book, shelf, isReference) " +
                "VALUES (1,'B1',1) ");
        statement.executeUpdate("INSERT INTO documents (id_book, shelf) " +
                "VALUES (2,'B2') ");
        statement.executeUpdate("INSERT INTO documents (id_book, shelf) " +
                "VALUES (3,'B3') ");
        statement.executeUpdate("INSERT INTO documents (id_book, shelf) " +
                "VALUES (3,'B1D') ");
        statement.executeUpdate("INSERT INTO documents (id_av, shelf) " +
                "VALUES (1,'AV1') ");
        statement.executeUpdate("INSERT INTO documents (id_journals, shelf) " +
                "VALUES (1,'JA1') ");
    }
}
