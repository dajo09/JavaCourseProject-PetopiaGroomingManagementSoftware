
package com.petopia.api;

import com.petopia.db.DatabaseConnections;
import com.petopiables.EmployeeAccess;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LogInQuery extends DatabaseConnections{
    String qUsername, level;
    int emp_id;
    
    public List<EmployeeAccess> retrieveAccess(String username, String password){
        
        List<EmployeeAccess> employeeAccess = new ArrayList();
        String query = "SELECT username, password, level, emp_id FROM access WHERE username = ? and password = ?";
        try {
            connectToDatabase();

            pStatement = connection.prepareStatement(query);
            pStatement.setString(1, username);
            pStatement.setString(2, password);           
            result = pStatement.executeQuery();
            
            while(result.next()){
                    qUsername = result.getString("username");
                    String qPassword = result.getString("password");
                    level = result.getString("level");
                    emp_id = result.getInt("emp_id");
                    employeeAccess.add(new EmployeeAccess(qUsername, qPassword, level, emp_id));
                    
                }
            closeAllConnections();
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
        return employeeAccess;
    }
}
