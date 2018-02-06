import java.sql.SQLException;

public class JournalArt extends Documents {

    /**
     *
     * @param title
     * @param author
     * @param journalName
     * @param issue
     * @param editor
     * @param shelf
     * @param canCheckout
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

    public static int getJournalID(int idDoc) throws SQLException {
        int idJournal = 0;
        resultSet = statement.executeQuery("SELECT id_journal FROM documents " +
                "WHERE id ='" + idDoc + "'");
        while (resultSet.next()) {
            idJournal = resultSet.getInt("id_journal");
            System.out.println("Journal id = " + idJournal);
        }
        if (idJournal == 0) {
            throw new NullPointerException("Something going wrong. This is not a Journal.");
        }
        return idJournal;
    }

    public static String[] getJournalName(int idDoc) throws SQLException {
        int idJournal = getJournalID(idDoc);

        String[] name = new String[2];
        resultSet = statement.executeQuery("SELECT * FROM journal_articles WHERE id = '" + idJournal + "'");
        while (resultSet.next()){
            name[0] = resultSet.getNString("title");
            name[1] = resultSet.getNString("author");
        }
        return name;
    }
}
