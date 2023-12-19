
package com.petopia.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public abstract class DatabaseConnections {
    //databasae connection parameters; PRIVATE, STATIC, FINAL
    private final static String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final static String URL = "jdbc:mysql://localhost:3306/petopia";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "";
    public Connection connection;
    public Statement state;
    public ResultSet result;
    public PreparedStatement pStatement;
    
    
    public void connectToDatabase(){
        try {
            //call the driver
            Class.forName(DRIVER);
            // create connnection
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException|ClassNotFoundException e) {
            System.out.println(e);
       }
    }
    
    
    public void closeAllConnections(){//CLEARS ALL CONNECTIONS: PREPAREDTATEMENT, RESULTSET AND CONNECTION
        try {
            if (pStatement != null) {
                pStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
             if (result != null) {
                result.close();
            }
        } catch (SQLException e) {
            // Handle the exception
            System.out.println("Error while closing the database connections: " + e.getMessage());
        }
    }
}
