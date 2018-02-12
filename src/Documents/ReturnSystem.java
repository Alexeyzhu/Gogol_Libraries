package Documents;

import DataBase.DBConnection;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

public class ReturnSystem {
    private static Statement statement;
    private static ResultSet resultSet;

    // Create statement
    static {
        try {
            statement = new DBConnection().setConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void returnDocument(int idUser, int idDoc) throws SQLException {
        Timestamp returnTime = new Timestamp(new Date().getTime());
        resultSet = statement.executeQuery("SELECT * from booking_sys " +
                "WHERE id_users = '" + idUser + " ' AND id_doc = '" + idDoc + "'");
        while (resultSet.next()){
            returnTime = resultSet.getTimestamp("returnTime");
        }
        if (returnTime.getNanos() < System.nanoTime()){

        }
    }
}
