package Users;

import DataBase.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;

public class Patrons extends Users {
    private static Statement statement;

    static {
        try {
            statement = new DBConnection().setConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Patrons() throws SQLException {
    }

    /**
     *  3 types : BOOK , JOURNAL , Documents.AV
     *
     */

    // Do smth with this method
    // Maybe static?
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
