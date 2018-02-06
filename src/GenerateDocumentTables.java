import java.sql.SQLException;
import java.sql.Statement;

public class GenerateDocumentTables {
    Statement statement;

    GenerateDocumentTables() throws SQLException {
        statement = new DBConnection().setConnection().createStatement();
    }

    public void createDocumentTables() throws SQLException {
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
}
