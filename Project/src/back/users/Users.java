package back.users;

import org.omg.CORBA.OBJECT_NOT_EXIST;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Users  {
    Statement statement;
    ResultSet resultSet;

    public Users() throws SQLException {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.setConnection();
        statement = connection.createStatement();
    }

    /**
     * User will be enter to the library only if
     * password and login right. GUI will get the level of access of user
     * Use it in order to pass users into system
     *
     * @param login login of user
     * @param password password of user to check
     * @return level of access library or patron
     * @throws SQLException, OBJECT_NOT_EXIST
     */
    public String getType(String login, String password) throws SQLException, OBJECT_NOT_EXIST {

        if (checkLoginPassword(login, password)) {
            String type = "";
            resultSet = statement.executeQuery("SELECT type FROM users " +
                    "WHERE id = '" + getID(login, password) + "'");
            while (resultSet.next()){
                type = resultSet.getString("type");
            }
            return type;
        } else {
            new OBJECT_NOT_EXIST("There is no such person");
            return null;
        }

    }

    /**
     * Returns the id of user according to its login and password
     *
     * @param login login of user
     * @param password password of user to check
     * @return id of person in the database
     * @throws SQLException
     */
    private int getID(String login, String password) throws SQLException {
        int id = 0;
        int pass = Integer.parseInt(password);
        resultSet = statement.executeQuery("SELECT id FROM users " +
                "WHERE login = '" + login + "' AND  password = '" + pass + "'");
        while (resultSet.next()) {
            id = resultSet.getInt("id");
        }
        System.out.println("There is ID of user : " + id);
        return id;
    }

    /**
     * Checks person's login and password if they are in the database
     * and suitable to each other
     *
     * @param login login of user
     * @param password password of user to check
     * @return true - if login and password are in system and belong to one person
     *         false - if password do not suitable to the login or login absent in system
     * @throws SQLException
     */
    public boolean checkLoginPassword(String login, String password) throws SQLException {
        String DBpass = null;
        String pass = password;

        resultSet = statement.executeQuery("SELECT * FROM users " +
                "WHERE login = '" + login + "'");
        while (resultSet.next()) {
            DBpass = resultSet.getString("password");
        }
        if(DBpass != null)return DBpass.equals(pass);
        return false;
    }
    
    public String[] getNS(String login, String pass) throws SQLException{
        String[] ns = new String[4];
        if(checkLoginPassword(login, pass)){
        resultSet = statement.executeQuery("SELECT * FROM users " + 
                "WHERE login = '" + login + "'");
        while(resultSet.next()){
            ns[0] = resultSet.getString("name");
            ns[1] = resultSet.getString("surname");
            ns[2] = resultSet.getString("address");
            ns[3] = resultSet.getString("phone");
        }
        return ns;
        }
        return null;
    }
    
}
