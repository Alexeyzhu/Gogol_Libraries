package back.users.docs;

import java.sql.SQLException;

public class AV extends Documents {
    AV() throws SQLException {
    }

    /**
     *
     * @param title of AV material
     * @param author  Who was created
     * @param shelf   Where it's placed
     * @param canCheckout Can this file be checked out?
     * @throws SQLException
     */
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
