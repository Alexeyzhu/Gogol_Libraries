import java.sql.SQLException;

public class Book extends Documents {
    Book() throws SQLException {
    }

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
