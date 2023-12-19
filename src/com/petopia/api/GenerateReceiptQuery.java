
package com.petopia.api;

import com.petopia.GenerateReceipt;
import com.petopia.db.DatabaseConnections;
import com.petopiables.Functions;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class GenerateReceiptQuery extends DatabaseConnections {
    //instantiations
    GenerateReceipt genRec = new GenerateReceipt();
    Functions fx = new Functions();
    
    public List<Integer> selectReceiptsId(){//list of inv_id returned
        List <Integer> invList = new ArrayList();
        String query = "SELECT inv_id FROM receipt";
        try {
            connectToDatabase();
            state = connection.createStatement();
            result = state.executeQuery(    query);
             while(result.next()){
                 int invId = result.getInt("inv_id");
                 invList.add(invId);
            }         
            closeAllConnections(); 
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
        return invList;
    }
    
    
    public List<Integer> selectReceiptsIdNotVoid(){//list of non-void receipts; for printing
        List <Integer> invList = new ArrayList();
        String query = "SELECT inv_id FROM receipt where void = 0";
        try {
            connectToDatabase();
            state = connection.createStatement();
            result = state.executeQuery(    query);
             while(result.next()){
                 int invId = result.getInt("inv_id");
                 invList.add(invId);
            }   
            closeAllConnections(); 
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
        return invList;
    }
    
    
    public void selectAllReceipts(){ //returns all void or not
        String query = this.queryStatement();

        try {
            connectToDatabase();
            state = connection.createStatement();
            result = state.executeQuery(    query);
            genRec.genRepFormat();
 
            
            if (result.next()){
                do {
                    int invid = result.getInt("receipt.inv_id");
                    java.sql.Date date = result.getDate("receipt.date");
                    String customer = result.getString("customer.fullname");
                    String pet = result.getString("pet.name");
                    String service = result.getString("service.desc");
                    String payment = result.getString("payment.desc");
                    float total = result.getFloat("receipt.total");
                    String employee = result.getString("employee.fullname");
                    System.out.printf("%3d %15s %-20s %-20s %-20s %-10s %-10.2f %-20s%n", 
                invid, date.toString(), customer, pet, service, payment, total, employee);
                    
                } while (result.next());
            } else {
                System.out.println("RECEIPT TABLE: \u001B[31mNo rows retrieved.\u001B[0m");  
            }           
            closeAllConnections(); 
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }           
    }
    
    
    public void selectAllReceiptsNotVoid(){
        String query = this.queryStatement() + "WHERE receipt.void = 0 ";//prints only active receipts

        try {
            connectToDatabase();
            state = connection.createStatement();
            result = state.executeQuery(    query);
            genRec.genRepFormat();
            
            if (result.next()){
                do {
                    int invid = result.getInt("receipt.inv_id");
                    java.sql.Date date = result.getDate("receipt.date");
                    String customer = result.getString("customer.fullname");
                    String pet = result.getString("pet.name");
                    String service = result.getString("service.desc");
                    String payment = result.getString("payment.desc");
                    float total = result.getFloat("receipt.total");
                    String employee = result.getString("employee.fullname");
                    System.out.printf("%3d %15s %-20s %-20s %-20s %-10s %-10.2f %-10s %n", 
                invid, date.toString(), customer, pet, service, payment, total, employee);
                    
                } while (result.next());
            } else {
                System.out.println("RECEIPT TABLE: No rows retrieved");  
            }             
            closeAllConnections(); 
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }         
    }
    
    
    public void voidReceipt(int invId) {
       String query = "UPDATE receipt SET void = 1 WHERE inv_id = ?";
       genRec.genRepFormat();
       
        try {
            connectToDatabase();
            pStatement = connection.prepareStatement(query);
           
            pStatement.setInt(1, invId);
            int rowsAffected = pStatement.executeUpdate();
            fx.cleanConsoleTwenty();
            System.out.println("VOID RECEIPT TABLE: " + rowsAffected + " row, \u001B[36mInvoice #" + invId + " VOIDED.\u001B[0m");
            
            this.selectVoidReceipt(invId);//return the now voided inv_id
            
            this.closeAllConnections(); 
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
    }
    
    
    public void selectVoidReceipt(int invId){
        String query = this.queryStatement() + "WHERE receipt.inv_id = ? ";
        

        try {
            connectToDatabase();
            
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, invId);
            result = pStatement.executeQuery();
            
            if (result.next()){
                do {
                    int invid = result.getInt("receipt.inv_id");
                    java.sql.Date date = result.getDate("receipt.date");
                    String customer = result.getString("customer.fullname");
                    String petname = result.getString("pet.name");
                    String service = result.getString("service.desc");
                    String payment = result.getString("payment.desc");
                    float total = result.getFloat("receipt.total");
                    String employee = result.getString("employee.fullname");
                    String voided = result.getString("receipt.void");
                    
                    String padInvId = String.format("%06d", invid);
                    System.out.format("\n+--------------------------------------------------------+%n");
                    System.out.format("|                     VOID RECEIPTS                      |%n");
                    System.out.format("|                     RECEIPT TABLE                      |%n");
                    System.out.format("+--------------------------------------------------------+%n\n");
                    System.out.printf("\t%-15s \t%-30s\n\t%-15s \t%-30s\n\t%-15s \t%-30s\n\t%-15s"
                            + " \t%-30s\n\t%-15s \t%-30s\n\t%-15s \t%-30s\n\t%-15s \t%-30.2f\n\t%-15s \t%-30s\n\t%-15s \t%-30s %n",
                    "RECEIPT #:", padInvId,
                    "DATE:", date,
                    "CUSTOMER:", customer,
                    "PET NAME:", petname,
                    "SERVICE", service,
                    "PAY TYPE:", payment,
                    "TOTAL:", total,
                    "EMPLOYEE", employee,
                    "VOID: ", voided);
                                       
                } while (result.next());
            } else {
                System.out.println("RECEIPT TABLE: No rows retrieved");  
                genRec.generateReceipt();
            }              
            closeAllConnections(); 
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
    }
    
    
    public void selectReceipt(int invId){
        String query = this.queryStatement() + "WHERE receipt.inv_id = ? ";
        
        try {
            connectToDatabase();
            
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, invId);
            result = pStatement.executeQuery();
            
            if (result.next()){
                do {
                    int invid = result.getInt("receipt.inv_id");
                    java.sql.Date date = result.getDate("receipt.date");
                    String customer = result.getString("customer.fullname");
                    String petname = result.getString("pet.name");
                    String service = result.getString("service.desc");
                    String payment = result.getString("payment.desc");
                    float total = result.getFloat("receipt.total");
                    String employee = result.getString("employee.fullname");
                    
                    String padInvId = String.format("%06d", invid);
                    
                    System.out.printf("\t%-15s \t%-30s\n\t%-15s \t%-30s\n\t%-15s \t%-30s\n\t%-15s"
                            + " \t%-30s\n\t%-15s \t%-30s\n\t%-15s \t%-30s\n\t%-15s \t%-30.2f %n",
                    "RECEIPT #:", padInvId,
                    "DATE:", date,
                    "CUSTOMER:", customer,
                    "PET NAME:", petname,
                    "SERVICE", service,
                    "PAY TYPE:", payment,
                    "TOTAL:", total);
                    
                    System.out.println("\n    You have been served by: " + employee + " today."+
                    "\n    Thank you for your business. Please come back again!");
                    
                } while (result.next());
            } else {
                System.out.println("RECEIPT TABLE: No rows retrieved");  
                genRec.generateReceipt();
            }              
            closeAllConnections(); 
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
    }
    
    
    public void selectReceiptAllFields(int invId){
        String query = this.queryStatement() + "WHERE receipt.inv_id = ? ";
        
        try {
            connectToDatabase();
            
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, invId);
            result = pStatement.executeQuery();
            
            if (result.next()){
                do {
                    int invid = result.getInt("receipt.inv_id");
                    java.sql.Date date = result.getDate("receipt.date");
                    String customer = result.getString("customer.fullname");
                    String petname = result.getString("pet.name");
                    String service = result.getString("service.desc");
                    String payment = result.getString("payment.desc");
                    float total = result.getFloat("receipt.total");
                    String employee = result.getString("employee.fullname");
                    int voided = result.getInt("receipt.void");
                    
                    String padInvId = String.format("%06d", invid);
                    
                    System.out.printf("\t%-15s \t%-30s\n\t%-15s \t%-30s\n\t%-15s \t%-30s\n\t%-15s"
                            + " \t%-30s\n\t%-15s \t%-30s\n\t%-15s \t%-30s\n\t%-15s \t%-30.2f"
                            + "\n\t%-15s \t%-30s\n\t%-15s \t%-30s%n",
                    "RECEIPT #:", padInvId,
                    "DATE:", date,
                    "CUSTOMER:", customer,
                    "PET NAME:", petname,
                    "SERVICE", service,
                    "PAY TYPE:", payment,
                    "TOTAL:", total,
                    "EMPLOYEE", employee,
                    "VOID:", voided);                   
                    
                } while (result.next());
            } else {
                System.out.println("RECEIPT TABLE: \u001B[31mNo rows retrieved.\u001B[0m");  
                genRec.viewReceipt();
            }              
            closeAllConnections(); 
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
    }
    
     
    String queryStatement(){
        String query = "SELECT receipt.inv_id, receipt.date, customer.fullname, "
                + "pet.name, service.desc, payment.desc, receipt.total, employee.fullname, receipt.void "
                + "FROM receipt\n" +
            "INNER JOIN customer\n" +
            "ON receipt.cust_id = customer.cust_id\n" +
            "INNER JOIN pet\n" +
            "ON receipt.pet_id = pet.pet_id\n" +
            "INNER JOIN service\n" +
            "ON receipt.serv_id = service.serv_id\n" +
            "INNER JOIN payment\n" +
            "ON receipt.pay_id = payment.pay_id\n" +
            "INNER JOIN employee\n" +
            "ON receipt.emp_id = employee.emp_id\n";

        return query;
    } 
}

    



