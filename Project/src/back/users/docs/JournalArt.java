package back.users.docs;

import java.sql.SQLException;

public class JournalArt extends Documents {
    JournalArt() throws SQLException {
    }

    /**
     *
     * @param title
     * @param author
     * @param journalName
     * @param issue
     * @param editor
     * @param shelf     where journal will be store
     * @param canCheckout can be checked out or not
     * @throws SQLException
     */
    public void addJA(String title, String author, String journalName,
                      String issue, String editor, String shelf,
                      boolean canCheckout) throws SQLException {
        int id_JA = 0;
        statement.executeUpdate("INSERT INTO journal_articles (title, author, journal_name, issue, editor) " +
                "VALUES ('" + title + "','" + author + "','" + journalName + "'," +
                "'" + issue + "','" + editor + "') ");
        resultSet = statement.executeQuery("SELECT id FROM journal_articles " +
                "WHERE title = '" + title + "' AND author = '" + author + "' AND journal_name = '" + journalName + "'" +
                " AND issue = '" + issue + "' AND editor = '" + editor + "' ");

        while (resultSet.next()) {
            id_JA = resultSet.getInt("id");
        }

        statement.executeUpdate("INSERT INTO documents (id_journal,shelf, canCheckout)" +
                "VALUES (" + id_JA + ",'" + shelf + "'," + canCheckout + ")");

    }
}
