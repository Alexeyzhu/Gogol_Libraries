import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;

public class Patrons extends Users {
    Patrons() throws SQLException {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.setConnection();
        statement = connection.createStatement();
    }

    /**
     *  3 types : BOOK , JOURNAL , AV
     *
     */

    public String getDocument(String type, String name, String author) {
        if (type.equals("BOOK") || type.equals("JOURNAL") || type.equals("AV")) {

            if (type.equals("BOOK") ){

            }else if (type.equals("JOURNAL") ){

            }else if (type.equals("AV") ){

            }
            return null;
        }else {
            throw new InputMismatchException(" Wrong type ");
        }
    }
}
