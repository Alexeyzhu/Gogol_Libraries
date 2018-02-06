package Documents;

import java.sql.SQLException;

public class AV extends Documents {
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
        int idAV = 0;

        statement.executeUpdate("INSERT INTO av (author, title) " +
                "VALUES ('" + author + "','" + title + "') ");
        resultSet = statement.executeQuery("SELECT id FROM av " +
                "WHERE author = '" + author + "' AND title = '" + title + "'");

        while (resultSet.next()) {
            idAV = resultSet.getInt("id");
        }

        statement.executeUpdate("INSERT INTO documents (id_av,shelf, canCheckout)" +
                "VALUES (" + idAV + ",'" + shelf + "'," + canCheckout + ")");

    }

    public static int getAVID(int idDoc) throws SQLException {
        final int AV_DOES_NOT_EXIST = 0;

        int idAV = AV_DOES_NOT_EXIST;
        resultSet = statement.executeQuery("SELECT id_av FROM documents " +
                "WHERE id ='" + idDoc + "'");

        while (resultSet.next()) {
            idAV = resultSet.getInt("id_av");
            System.out.println("The Documents.AV id = " + idAV);
        }

        if (idAV == AV_DOES_NOT_EXIST) {
            throw new NullPointerException("Something going wrong. This is not a Documents.AV.");
        }

        return idAV;
    }

    public static String[] getAVName(int idDoc) throws SQLException {
        int idAV = getAVID(idDoc);

        resultSet = statement.executeQuery("SELECT * FROM av WHERE id = '" + idAV + "'");
        String[] name = new String[2];
        while (resultSet.next()){
            name[0] = resultSet.getNString("title");
            name[1] = resultSet.getNString("author");
        }

        return name;
    }
}
