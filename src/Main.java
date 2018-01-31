import org.json.JSONObject;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

       // Booking booking = new Booking();
       // booking.checkOut(1, 2);
        Users users = new Users();
       // users.createPerson("Dan", "Zhuchkov", "Innopolis", 123456789, "L");
        System.out.println(users.checkLoginPassword("n.zhuchkov","305155464") + " :answer");

        //  LogIn logIn = new LogIn();
        //  logIn.setVisible(true);
    }


}
