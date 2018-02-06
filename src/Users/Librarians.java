package Users;

import javax.management.InstanceAlreadyExistsException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Librarians extends Users {

    public Librarians() throws SQLException {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.setConnection();
        statement = connection.createStatement();
    }

    Statement statement;
    ResultSet resultSet;

    /**
     * Create user in the table users
     * Also checks if already there is exist this person
     *
     * @param name    input name
     * @param surname input surname
     * @param address input address
     * @param phone   input phone
     * @param type    input type of user (Library, Users.Student, Users.Faculty )
     * @return array with generated login and password
     * @throws SQLException
     */
    public String[] addPerson(String name, String surname, String address, String phone, String type)
            throws SQLException, InstanceAlreadyExistsException {
        final int DEFAULT = 0;
        final int PERSON_ALREADY_EXIST = 1;
        final int STANDART_PHONE_COUNT = 11;

        int result = DEFAULT;

        if (name == null || surname == null || address == null || phone == null ||
                name.equals("Name") || surname.equals("Surname") || address.equals("Address")) {
            throw new NullPointerException("Some of parameters are null");
        } else if (phone.length() != STANDART_PHONE_COUNT) {
            throw new NumberFormatException("Wrong phone format");
        } else {

            //check if there is exist already this person
            resultSet = statement.executeQuery("SELECT EXISTS(SELECT id FROM users " +
                    "WHERE name = '" + name + "' AND surname = '" + surname + "')");

            while (resultSet.next()) {
                result = resultSet.getInt(PERSON_ALREADY_EXIST);
            }

            if (result == PERSON_ALREADY_EXIST) {
                throw new InstanceAlreadyExistsException("There is already exist this person");
            } else {
                String[] data = generateLogin(name, surname);
                statement.executeUpdate("INSERT INTO users (login, password, name, surname, address, phone, type) " +
                        "VALUES ('" + data[0] + "','" + data[1] + "','" + name + "','" + surname + "','" + address + "'," + phone + ",'" + type + "')");
                return data;
            }
        }

    }

    /**
     * Generator of login and password
     * Login form first_char_of_name.full_surname
     * Password : 8 numbers
     *
     * @param name    input name
     * @param surname input surname
     * @return array with generated login and password
     * @throws SQLException
     */
    private String[] generateLogin(String name, String surname) throws SQLException {
        // Convert to lower case
        name = name.toLowerCase();
        surname = surname.toLowerCase();

        // create login and password
        String login = name.substring(0, 1) + "." + surname;
        String password = Integer.toString(hashFunction(name, surname));
        return new String[]{login, password};
    }

    /**
     * Hash function which generates password according to
     * the name and surname
     *
     * @param name    input name
     * @param surname input surname
     * @return
     */
    private int hashFunction(String name, String surname) {
        final int MAX_PASSWORD_LENGTH = 8;
        final int BASE_OF_ARITHMETIC = 10;
        final int MINIMUM_DATA_LENGTH = 10;

        String data = name + surname;
        while (data.length() < MINIMUM_DATA_LENGTH) {
            data += data;
        }

        int hash = Math.abs(data.hashCode());

        return (int) (hash % Math.pow(BASE_OF_ARITHMETIC, MAX_PASSWORD_LENGTH));
    }
}
