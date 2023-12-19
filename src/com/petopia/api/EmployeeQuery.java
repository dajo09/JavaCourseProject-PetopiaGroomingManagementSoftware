
package com.petopia.api;

import com.petopia.EmployeeInfo;
import com.petopia.db.DatabaseConnections;
import com.petopiables.Employee;
import com.petopiables.Functions;
import com.petopiables.PetopiaHeader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeQuery extends DatabaseConnections {
    
    PetopiaHeader petHeader = new PetopiaHeader();
    Functions fx = new Functions();
    
    public void addNewEmployee(String fullname, String email, String address, String position){
        String query = "INSERT INTO employee (fullname, email, address, position) VALUES (?, ?, ?, ?)";
        
        try {
            connectToDatabase();
            
            //prepareStatement
            pStatement = connection.prepareStatement(query);
            pStatement.setString(1, fullname);
            pStatement.setString(2, email);
            pStatement.setString(3, address);
            pStatement.setString(4, position);
          
            //insert the data
            int check = pStatement.executeUpdate();
            
            if (check > 0){
            System.out.println("\nEMPLOYEE TABLE: \u001B[36mEmployee '" + fullname + "' successfully added!\u001B[0m");
            } else {
                System.out.println("Insertion failed.");
            }          
            closeAllConnections(); //close all DB connections(pStatement, connection)          
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
    }
    
    
    public List<Integer> searchEmployeeLike(String name){
        EmployeeInfo empInfo = new EmployeeInfo();
        List <Integer> likeList = new ArrayList();
        String query = "SELECT * FROM employee WHERE fullname LIKE ? ";
        try {
            connectToDatabase();
            
            pStatement = connection.prepareStatement(query);
            pStatement.setString(1, name + "%");  
            result = pStatement.executeQuery();
            
            if (!result.next()){
                System.out.println("EMPLOYEE TABLE:  \u001B[31mNo rows were retrieved.\u001B[0m");
                fx.xToCancel(empInfo::employeeInfo);

            } else {
                petHeader.updateFormat();
                petHeader.empFormat();//employee format
                System.out.format("| %-7s | %-27s |%n", "EMP_ID", "FULLNAME");
                System.out.format("+---------------------------------------+%n");
            
                do {
                int empId = result.getInt("emp_id");
                String fullname = result.getString("fullname");
                String email = result.getString("email");
                String address = result.getString("address");
                String position = result.getString("position");
                int archived = result.getInt("archived");
                likeList.add(empId);
                Employee employee = new Employee(empId, fullname, email, address, position, archived);
               
                System.out.format("| %-7s | %-27s |%n",  
                        employee.getEmpId(), employee.getFullname());
                    
            } while (result.next());
              
            }
            closeAllConnections(); 
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
        return likeList;
    }
    
    
    public int selectEmployeeFields(int empId){
        int empRet = 0;
        
        String query = "SELECT * FROM employee WHERE emp_id = ? ";
        try {
            connectToDatabase();
            
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, empId);          
            result = pStatement.executeQuery();
            
            if (result.next()){
                empRet = 1;
                do {
                    String fullname = result.getString("fullname");
                    String email = result.getString("email");
                    String address = result.getString("address");
                    String position = result.getString("position");
                    String archived = result.getString("archived");
                    petHeader.updateFormat();
                    petHeader.empFormat();
                    System.out.printf("  (1) FULLNAME:  %-20s\n  (2) EMAIL:     %-30s\n  (3) ADDRESS:   %-50s\n  (4) POSITION:  %-20s\n  (5) ARCHIVED:  %-10s\n", 
                  fullname, email, address, position, archived);
     
                } while (result.next());
            } else {
                empRet = 0;
                System.out.println("EMPLOYEE TABLE: \u001B[31mNo rows retrieved.\u001B[0m");               
            }
            closeAllConnections(); 
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }  
        return empRet;
    }
    
    
    public int selectEmployeeFieldsRet(int empId){
        int rowsRetrieved = 0;
        String query = "SELECT * FROM employee WHERE emp_id = ? ";
        try {
            connectToDatabase();
            
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, empId);
            result = pStatement.executeQuery();
            
            if (result.next()){
                do {
                    String fullname = result.getString("fullname");
                    String email = result.getString("email");
                    String address = result.getString("address");
                    String position = result.getString("position");
                    String archived = result.getString("archived");
                    petHeader.empFormat();
                    System.out.printf("\n  (1) FULLNAME:  %-20s\n  (2) EMAIL:     %-30s\n  (3) ADDRESS:   %-50s\n  (4) POSITION:  %-20s\n  (5) ARCHIVED:  %-10s\n", 
                  fullname, email, address, position, archived);

                    rowsRetrieved = 1;
                } while (result.next());
            } else {
                System.out.println("EMPLOYEE TABLE: \u001B[31mNo rows retrieved.\u001B[0m");
                rowsRetrieved = 0;
            }
            closeAllConnections(); 
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
        return rowsRetrieved;
    }
    
    
    public void updateEmployee(String field, String newData, int empId){
        String query = " UPDATE employee SET " + field + " = ? WHERE emp_id = ? ";
        
        try {
            connectToDatabase();
            pStatement = connection.prepareStatement(query);
            pStatement.setString(1, newData);
            pStatement.setInt(2, empId);
            
            int rowsAffected = pStatement.executeUpdate();
            System.out.println("\nEMPLOYEE TABLE: \u001B[36m" + rowsAffected + " row," + empId + " UPDATED.\u001B[0m");
            
            this.selectEmployeeFields(empId);
            closeAllConnections(); 
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
    }
    
    
    public void displayAllEmployee(){
        String query = "SELECT * FROM employee ";
        try {
            connectToDatabase();
            state = connection.createStatement();
            result = state.executeQuery(    query);
            petHeader.empFormatExtended();
            System.out.printf("%-10s %-20s %-20s %-30s %-25s %-10s %n\n", "EMP_ID",
                    "FULLNAME", "EMAIL", "ADDRESS", "POSITION", "ARCHIVED");
            
            while(result.next()){
                int empId = result.getInt("emp_id");
                String name = result.getString("fullname");
                String email = result.getString("email");
                String address = result.getString("address");
                String position = result.getString("position");  
                String archived = result.getString("archived"); 

                System.out.printf("%-10d %-20s %-20s %-30s %-25s %5s%n",
                        empId, name, email, address, position, archived);

            } 
            closeAllConnections(); 
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
    }
    
    
    public int deleteEmployee(int empId){
        String query = "DELETE FROM employee WHERE emp_id = ? ";
        int rowsAffected = 0;
        try {
            connectToDatabase();
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, empId);

            rowsAffected = pStatement.executeUpdate();
            System.out.println("\nEMPLOYEE TABLE: \u001B[36m" +  rowsAffected + " row, EMP_ID: " + empId + " DELETED.\u001B[0m");

            closeAllConnections(); 
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
        return rowsAffected;
    }
    
    
    public void displayAllAccess(){
        final String query = "SELECT * FROM access";
        try {
            connectToDatabase();
            state = connection.createStatement();
            result = state.executeQuery(    query);
            petHeader.accessFormat();
            System.out.printf("%-10s %-20s %-20s %-20s%n\n", 
                    "EMP_ID", "USERNAME", "PASSWORD", "LEVEL");
            
            while(result.next()){
                String user = result.getString("username");
                String pass = result.getString("password");
                String lvl = result.getString("level");
                int id = result.getInt("emp_id");
                
                System.out.printf("%-10s %-20s %-20s %-20s%n", 
                    id, user, pass, lvl);       
            }  
            closeAllConnections(); 
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
    }
    
    
    public void deleteAccessById(int empId){//by emp_id
        String query = "DELETE FROM access WHERE emp_id = ? ";
        
        try {
            connectToDatabase();
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, empId);

            int rowsAffected = pStatement.executeUpdate();
            System.out.println("\nACCESS TABLE: \u001B[36m" +  rowsAffected + " row, EMP_ID: " + empId + " DELETED.\u001B[0m");
            
            closeAllConnections(); 
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }    
    }
    
    
    public void deleteAccessByUserPass(String username, String password){//by emp_id
        int rowsAffected = 0;
        String query = "DELETE FROM access WHERE username = ? and password = ?";
        
        try {
            connectToDatabase();
            pStatement = connection.prepareStatement(query);
            pStatement.setString(1, username);
            pStatement.setString(2, password);

            rowsAffected = pStatement.executeUpdate();
            
            if (rowsAffected > 0){
                System.out.println("\nACCESS TABLE: \u001B[36m" +  rowsAffected + " row, " +
                    username + " & " + password + " DELETED.\u001B[0m");
            } else {
                System.out.println("DELETE EMPLOYEE ACCESS: \u001B[36mno row DELETED.\u001B[0m");
            }
            closeAllConnections(); 
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
    }
    
    
    public void insertAccess(String user, String pass, String level, int empId){
        String query = "INSERT INTO access (username, password, level, emp_id) VALUES (?, ?, ?, ?) ";
        try {
            connectToDatabase();
            //prepareStatement
            pStatement = connection.prepareStatement(query);
            pStatement.setString(1, user);
            pStatement.setString(2, pass);
            pStatement.setString(3, level);
            pStatement.setInt(4, empId);//NEW EMPLOYEE DEFAULTS TO 0
          
            //insert the data
            int check = pStatement.executeUpdate();
            
            if (check > 0){
            System.out.println("\nACCESS TABLE: \u001B[36mEmployee with EMP_ID: " + empId + " successfully added.\u001B[0m");
            } else {
                System.out.println("Insertion failed.");
            }        
            closeAllConnections(); 
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }         
    }
 
    
    public List<Integer> selectAccEmpId(){
        List <Integer> accList = new ArrayList();
        String query = "SELECT emp_id from access ";
        
        try {
            connectToDatabase();
            
            state = connection.createStatement();
            result = state.executeQuery(    query);
            
            while(result.next()){
                int id = result.getInt("emp_id");
                accList.add(id);
            }
            closeAllConnections(); 
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
        return accList;
    }
    
    
    public void selectAccess(int empId){
        String query = "SELECT * FROM access WHERE emp_id = ?";
        try {
            connectToDatabase();
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, empId);   
            result = pStatement.executeQuery();
            
            petHeader.accessFormat();
            System.out.printf("%-10s %-20s %-20s %-20s%n\n", 
                    "EMP_ID", "USERNAME", "PASSWORD", "LEVEL");
            
            while(result.next()){
                String user = result.getString("username");
                String pass = result.getString("password");
                String lvl = result.getString("level");
                int id = result.getInt("emp_id");
                
                System.out.printf("%-10s %-20s %-20s %-20s%n", 
                    id, user, pass, lvl);         
            }
            closeAllConnections(); 
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
    }  
}          
    



           