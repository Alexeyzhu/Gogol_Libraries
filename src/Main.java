import org.json.JSONObject;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        DBConnection dbConnection = new DBConnection();
//        Connection connection = dbConnection.setConnection();
//        Statement statement = connection.createStatement();
        Booking booking = new Booking();
        booking.checkOut(1,1);

//        statement.executeUpdate("INSERT INTO library.books (name,author,publisher,edition,edition_year) VALUES ('Terminal','george','aast',15,1555)");
//        statement.executeUpdate("INSERT INTO library.documents (id_book,shelf,canCheckout) VALUES (1,'B4',TRUE )");
//        ResultSet resultSet = statement.executeQuery("SELECT *FROM library.documents WHERE id_book = '1'");
//
//        while (resultSet.next()) {
//            //   System.out.println(resultSet.getInt(1));
//            System.out.println(resultSet.getString("canCheckout"));
//            System.out.println("--------");
//
//        }
      //  LogIn logIn = new LogIn();
      //  logIn.setVisible(true);
    }


}
