import org.json.JSONObject;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Booking booking = new Booking();
        booking.checkOut(1,2);
        Users users = new Users();
        users.createPerson("svbgt","asf","asdf",562,"afgrt");


      //  LogIn logIn = new LogIn();
      //  logIn.setVisible(true);
    }


}
