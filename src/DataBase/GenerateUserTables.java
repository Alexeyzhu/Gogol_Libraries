package DataBase;

import java.sql.SQLException;
import java.sql.Statement;

public class GenerateUserTables {
    private static Statement statement;

    static {
        try {
            statement = new DBConnection().setConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createUserTable() throws SQLException {
        statement.executeUpdate("CREATE TABLE users " +
                "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "login VARCHAR (30) NOT NULL," +
                "password VARCHAR (20) NOT NULL," +
                "name VARCHAR (20) NOT NULL ," +
                "surname VARCHAR (30) NOT NULL ," +
                "address VARCHAR (100) NOT NULL," +
                "phone TEXT NOT NULL ," +
                "type VARCHAR (20) NOT NULL" +
                ")");
        statement.executeUpdate("INSERT INTO users (login, password, name, surname, address, phone, type) " +
                "VALUES ('admin','12345','Victor','Rivera','Russia,Kazan','79521234567','Librarian' ) ");
        statement.executeUpdate("INSERT INTO users (login, password, name, surname, address, phone, type) " +
                "VALUES ('f.galeev','1111111','Farit','Galeev','Russia,Innoplois','79527894661','Faculty') ");
        statement.executeUpdate("INSERT INTO users (login, password, name, surname, address, phone, type) " +
                "VALUES ('a.zhuchkov','2222222','Alexey','Zhuchkov','Russia,Innopolis','79521234566',Users.Student) ");
        statement.executeUpdate("INSERT INTO users (login, password, name, surname, address, phone, type) " +
                "VALUES ('i.mazan','3333333','Ilya','Mazan','Russia,Innopolis','79521234589','Student') ");
    }

}
