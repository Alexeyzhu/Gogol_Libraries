import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Booking {
    Statement statement;
    ResultSet resultSet;

    Booking() throws SQLException {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.setConnection();
        statement = connection.createStatement();
    }

    public void checkOut(int id_us, int id_doc) throws SQLException {
        boolean canCheckout = false;
        Date date = new Date();
        System.out.println(id_doc);
        resultSet = statement.executeQuery("SELECT * FROM library.documents WHERE id = '" + id_doc + "'");

        while (resultSet.next()) {
            canCheckout = resultSet.getBoolean(6);
            System.out.println(canCheckout);
        }

        if (canCheckout) {
            statement.executeUpdate("UPDATE library.documents SET canCheckout = FALSE WHERE id = '" + id_doc + "'");
            java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
            statement.executeUpdate("INSERT INTO library.booking_sys (id_users, id_doc, checkout_time, isRenewed) " +
                    "VALUES ('" + id_us + "','" + id_doc + "','" + timestamp + "',FALSE )"); }
    }
}
