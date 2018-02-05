/*
 * Creates table model component for JTable class.
 */
package project.GUI;

import back.users.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Farit Galeev
 */
public class UserTableModel extends AbstractTableModel{
    private ArrayList<String[]> dataList;
    Statement statement;
    ResultSet resultSet;

    public UserTableModel() throws SQLException {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.setConnection();
        statement = connection.createStatement();
        dataList = new ArrayList<String[]>();
        for (int i = 0; i < dataList.size(); i++) {
            dataList.add(new String[getColumnCount()]);
        }
    }
    
    @Override
    public int getRowCount() {
        return dataList.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int r, int c) {
        String[] rows = dataList.get(r);
        return rows[c];
    }
    
    @Override
    public String getColumnName(int ci){
        switch(ci){
            case 0: return "Name";
            case 1: return "Surname";
            case 2: return "Address";
            case 3: return "Phone Number";
            case 4: return "Card Number";
        }
        
        return null;
    }
    
    public void addData(String[] row){
        String[] rowTable = new String[getColumnCount()];
        rowTable = row;
        dataList.add(rowTable);
    }
    
    public void addData() throws SQLException{
        resultSet = statement.executeQuery("SELECT * FROM users");
        while(resultSet.next()){
            String[] row = {
                resultSet.getString("name"),
                resultSet.getString("surname"),
                resultSet.getString("address"),
                resultSet.getString("phone"),
                resultSet.getString("id")
            };
            addData(row);
        }
    }
}
