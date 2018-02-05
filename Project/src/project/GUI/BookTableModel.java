/*
 * Creates table model component for JTable class 
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
public class BookTableModel extends AbstractTableModel {
    private ArrayList<String[]> dataList;
    Statement statement;
    ResultSet resultSet;
   
    public BookTableModel() throws SQLException{
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
    public String getColumnName(int ci){
        switch(ci){
            case 0: return "Title";
            case 1: return "Author";
            case 2: return "Publisher";
            case 3: return "Edition";
            case 4: return "Edition year";
        }
        
        return null;
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String[] rows = dataList.get(rowIndex);
        return rows[columnIndex];
    }
    
     public void addData(String[] row){
        String[] rowTable = new String[getColumnCount()];
        rowTable = row;
        dataList.add(rowTable);
    }
    
    public void addData() throws SQLException{
        resultSet = statement.executeQuery("SELECT * FROM books");
        while(resultSet.next()){
            String[] row = {
                resultSet.getString("name"),
                resultSet.getString("author"),
                resultSet.getString("publisher"),
                resultSet.getString("edition"),
                resultSet.getString("edition_year")
            };
            addData(row);
        }
    }
    
}
