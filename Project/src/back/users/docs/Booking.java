package back.users.docs;

import back.users.DBConnection;
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

    /**
     * Insert user's and document's ID into booking table and
     * system gives unique ID to this booking transaction
     *
     * @param id_us id of user from user table
     * @param id_doc id of document from document table
     * @throws SQLException
     */
    public void checkOut(int id_us, int id_doc) throws SQLException {
        Documents documents = new Documents();
        if (documents.canCheckOut(id_doc)) {
            documents.setCanCheckout(id_doc, false);
            addBooking(id_us, id_doc);
        }
    }

    /**
     * Insert user's and document's into to booking table
     *
     * @param id_us id of user from user table
     * @param id_doc id of document from document table
     * @throws SQLException
     */
    private void addBooking(int id_us, int id_doc) throws SQLException {
        Date date = new Date();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
        statement.executeUpdate("INSERT INTO booking_sys (id_users, id_doc, checkout_time, isRenewed) " +
                "VALUES ('" + id_us + "','" + id_doc + "','" + timestamp + "',FALSE )");
    }
}