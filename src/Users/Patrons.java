package Users;

import DataBase.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;

public class Patrons extends Users {
    public Patrons() throws SQLException {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.setConnection();
        statement = connection.createStatement();
    }

    /**
     *  3 types : BOOK , JOURNAL , Documents.AV
     *
     */

    public String getDocument(String type, String name, String author) {
        if (type.equals("BOOK") || type.equals("JOURNAL") || type.equals("Documents.AV")) {

            if (type.equals("BOOK") ){

            }else if (type.equals("JOURNAL") ){

            }else if (type.equals("Documents.AV") ){

            }
            return null;
        }else {
            throw new InputMismatchException(" Wrong type ");
        }
    }
}
