package DataBase;

import java.sql.SQLException;
import java.sql.Statement;

public class DropAllTables {
    private static Statement statement;

    static {
        try {
            statement = new DBConnection().setConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete all tables from the database
     *
     * @throws SQLException
     */
    public void dropAllTables() throws SQLException {
        statement.executeUpdate("DROP TABLE av");
        statement.executeUpdate("DROP TABLE books");
        statement.executeUpdate("DROP TABLE journals");
        statement.executeUpdate("DROP TABLE documents");
        statement.executeUpdate("DROP TABLE booking_sys");
        statement.executeUpdate("DROP TABLE users");
    }

}
