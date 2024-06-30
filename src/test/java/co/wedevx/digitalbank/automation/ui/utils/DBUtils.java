package co.wedevx.digitalbank.automation.ui.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static co.wedevx.digitalbank.automation.ui.utils.ConfigReader.getPropertiesValue;

public class DBUtils {

    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet resultSet = null;

    //get connection
    public static void establishSQLConnection(){
//        String url = "jdbc:mysql://3.249.240.23:3306/kamilaa2040";
//        String username = "kamilaa2040";
//        String password = "mxrvjjpcanbazgzg";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    getPropertiesValue("digitalbank.db.url"),
                    getPropertiesValue("digitalbank.db.username"),
                    getPropertiesValue("digitalbank.db.password"));

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    //select query - list<map<string,obj>>
    public static List<Map<String,Object>> runSQLSelectQuery (String sqlQuery){
        List<Map<String,Object>> rowsList = new ArrayList<>();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();

            while (resultSet.next()) {
                Map<String,Object> resultMap = new HashMap<>();
                for (int col = 1; col <= columnCount; col++) {
                    resultMap.put(resultSetMetaData.getColumnName(col),resultSet.getObject(col));
                } rowsList.add(resultMap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } if (rowsList.isEmpty()) {
            throw new RuntimeException("The selected table is empty");
        }
        return rowsList;
    }

    //update query - int
    public static int runSQLUpdateQuery(String sqlQuery) {
        int rowsUpdated = 0;
        try {

            statement = connection.createStatement();
            rowsUpdated = statement.executeUpdate(sqlQuery);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rowsUpdated;
    }

    //delete, truncate
    public static boolean runSQLDeleteQuery(String sqlQuery) {
        boolean isDeleted;
        int numOfRowsDeleted;
        try {
            statement = connection.createStatement();
            numOfRowsDeleted = statement.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } return numOfRowsDeleted > 0;
    }

    //insert into - int
    public static boolean runSQLInsertIntoQuery(String sqlQuery) {
        boolean isInserted;
        try {
            statement = connection.createStatement();
            isInserted = statement.execute(sqlQuery);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } return isInserted;
    }

    //close connection
    public static void closeConnection(){
        try {
            if (connection != null) {
                connection.close();
            } if (statement != null) {
                statement.close();
            } if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean existsInTheDB(String query) {
        try {
            Map<String,Object> queryOutputMap = runSQLSelectQuery(query).get(0);
            if (queryOutputMap.isEmpty()) return false;
        } catch (RuntimeException e) {
            return false;
        } return true;
    }

    public static List<Map<String,Object>> getFirstRowData(String sqlQuery){
        List<Map<String,Object>> firstRowDataList = new ArrayList<>();
        Map<String,Object> firstRowDataMap = new HashMap<>();

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();

            if (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    firstRowDataMap.put(resultSetMetaData.getColumnName(i),resultSet.getObject(i));
                }
            } if (firstRowDataMap.isEmpty()) {
                throw new RuntimeException("The selected table is empty");
            } firstRowDataList.add(firstRowDataMap);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return firstRowDataList;
    }

    public static List<Map<String,Object>> getLastRowData(String sqlQuery){
        List<Map<String,Object>> firstRowDataList = new ArrayList<>();
        Map<String,Object> firstRowDataMap = new HashMap<>();

//        sqlQuery = sqlQuery.replaceAll(";","").trim().toLowerCase();
//        String tableName = null;
//        String partialTableName = sqlQuery.substring(sqlQuery.indexOf("from") + 5).trim();
//        if (partialTableName.contains(" ")){
//            tableName = partialTableName.substring(0,partialTableName.indexOf(" "));
//        } else {
//            tableName = partialTableName;
//        }

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();

            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    firstRowDataMap.put(resultSetMetaData.getColumnName(i),resultSet.getObject(i));
                }
            } if (firstRowDataMap.isEmpty()) {
                throw new RuntimeException("The selected table is empty");
            } firstRowDataList.add(firstRowDataMap);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return firstRowDataList;
    }

    public static List<Map<String,Object>> getNthRowData(String sqlQuery, int rowNumber){
        List<Map<String,Object>> firstRowDataList = new ArrayList<>();
        Map<String,Object> firstRowDataMap = new HashMap<>();
        int counter = 0;
        if (rowNumber <= 0) {
            throw new RuntimeException("Invalid row number. It can't be less than 1");
        }
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();

            while (resultSet.next()) {
                counter++;
                if (counter == rowNumber) {
                    for (int i = 1; i <= columnCount; i++) {
                        firstRowDataMap.put(resultSetMetaData.getColumnName(i),resultSet.getObject(i));
                    } break;
                }
            } if (counter == 0) {
                throw new RuntimeException("The selected table is empty");
            } else if (rowNumber > counter) {
                throw new RuntimeException("Invalid row number requested. The selected table only has " + counter + " rows.");
            } firstRowDataList.add(firstRowDataMap);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return firstRowDataList;
    }
//    Return the nth row from the table as a list of maps:
//      The method should return the nth row if the table is not null and the requested row number is present in the table
//      The method should accept a positive integer as the nth-row number
//      The return type is a list of maps, where the key is a string and the value is an object
//      Handle exceptions


//    public static int getTableLength(String tableName) {
//
//        try {
//            statement = connection.createStatement();
//            resultSet = statement.executeQuery("Select COUNT(*) from ")
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//    }






}
