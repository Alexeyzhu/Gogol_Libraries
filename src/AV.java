import java.sql.SQLException;

public class AV extends Documents {
    AV() throws SQLException {
    }

    /**
     *
     * @param title
     * @param author
     * @param shelf
     * @param canCheckout
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

    public static int getAVID(int idDoc) throws SQLException {
        int idAV = 0;
        resultSet = statement.executeQuery("SELECT id_av FROM documents " +
                "WHERE id ='" + idDoc + "'");
        while (resultSet.next()) {
            idAV = resultSet.getInt("id_av");
            System.out.println(idAV);
        }
        if (idAV == 0) {
            throw new NullPointerException("Something going wrong. This is not a AV.");
        }
        return idAV;
    }

    public static String[] getAVName(int idDoc) throws SQLException {
        int idAV = getAVID(idDoc);

        String[] name = new String[2];
        resultSet = statement.executeQuery("SELECT * FROM av WHERE id = '" + idAV + "'");
        while (resultSet.next()){
            name[0] = resultSet.getNString("title");
            name[1] = resultSet.getNString("author");
        }
        return name;
    }
}
