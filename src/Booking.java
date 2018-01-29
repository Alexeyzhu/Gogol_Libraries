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
        Documents documents = new Documents();
        documents.getShelf(id_doc);
        canCheckout = documents.canCheckout(id_doc);

        if (canCheckout) {
            documents.setCanCheckout(id_doc, false);
            addBooking(id_us, id_doc);
        }
    }

    private void addBooking(int id_us, int id_doc) throws SQLException {
        Date date = new Date();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
        statement.executeUpdate("INSERT INTO library.booking_sys (id_users, id_doc, checkout_time, isRenewed) " +
                "VALUES ('" + id_us + "','" + id_doc + "','" + timestamp + "',FALSE )");
    }
}
