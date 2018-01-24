import java.sql.*;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String userName = "root";
        String password = "1234";
        String connectionUrl = "jdbc:mysql://localhost:3306/mysql?autoReconnect=true&useSSL=false";
        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager.getConnection(connectionUrl, userName, password);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE books");
            statement.executeUpdate("CREATE TABLE books (id mediumint NOT NULL auto_increment, " +
                    "name VARCHAR (30),img LONGBLOB,PRIMARY KEY (id));");
            statement.executeUpdate("INSERT INTO books (name) VALUES ('Terminal')");
            statement.executeUpdate("INSERT INTO books SET name = 'Telegram'");
            statement.executeUpdate("INSERT INTO books SET name = 'Telegram'");
            ResultSet resultSet = statement.executeQuery("SELECT *FROM books WHERE id = '1'");
            while (resultSet.next()) {
                //   System.out.println(resultSet.getInt(1));
                System.out.println(resultSet.getString(2));
                System.out.println("--------");
            }

            LogIn logIn = new LogIn();
            logIn.setVisible(true);
        }
    }
}
