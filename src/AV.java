import java.sql.SQLException;

public class AV extends Documents {
    AV() throws SQLException {
    }

    public void addAV(String title, String author, String shelf,
                        boolean canCheckout) throws SQLException {
        int id_AV = 0;

        statement.executeUpdate("INSERT INTO av (author, title) " +
                "VALUES ('" + author + "','" + title + "') ");
        resultSet = statement.executeQuery("SELECT id FROM av " +
                "WHERE author = '" + author + "' AND title = '" + title + "'");

        while (resultSet.next()) {
            id_AV = resultSet.getInt("id");
        }

        statement.executeUpdate("INSERT INTO documents (id_av,shelf, canCheckout)" +
                "VALUES (" + id_AV + ",'" + shelf + "'," + canCheckout + ")");

    }
}
