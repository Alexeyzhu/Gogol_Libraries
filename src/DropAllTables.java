import java.sql.SQLException;
import java.sql.Statement;

public class DropAllTables {
    Statement statement;
    DropAllTables() throws SQLException {
        statement = new DBConnection().setConnection().createStatement();
    }

    public void dropAllTables() throws SQLException {
        statement.executeUpdate("DROP TABLE av");
        statement.executeUpdate("DROP TABLE books");
        statement.executeUpdate("DROP TABLE journals");
        statement.executeUpdate("DROP TABLE documents");
        statement.executeUpdate("DROP TABLE booking_sys");
        statement.executeUpdate("DROP TABLE users");
    }

}
