import org.json.JSONObject;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Booking booking = new Booking();
        booking.checkOut(1, 2);
        Users users = new Users();
        users.createPerson("Alexey", "Zhuchkov", "Innopolis", 123456789, "L");


        //  LogIn logIn = new LogIn();
        //  logIn.setVisible(true);
    }


}
