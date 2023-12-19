
package com.petopia.api;

import com.petopia.CustomerInfo;
import com.petopia.db.DatabaseConnections;
import java.sql.SQLException;
import java.util.Scanner;

public class CustomerQuery extends DatabaseConnections{
    Scanner sc = new Scanner(System.in);
    CustomerInfo custInfo = new CustomerInfo();
    public int addCustomer(){
        int custId = -1;
        String query = "INSERT INTO CUSTOMER(fullname, email, address, phone) VALUES "
                + "(?, ?, ?, ?)"; // Anti SQL Injection
        
        try {
            
            System.out.println("Enter New Customer Information"); 
               //Customer Id will be auto-generated
            System.out.print("FULL NAME: ");
            String fullName = sc.nextLine();
            System.out.print("EMAIL: ");
            String email = sc.nextLine();
            System.out.print("ADDRESS: ");
            String address = sc.nextLine();
            int phone;

            // Input validation for phone number (must be an integer)
            while (true) {
                try {
                    System.out.print("PHONE: ");
                    phone = Integer.parseInt(sc.nextLine());
                    break; // Exit the loop if the input is valid
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid integer for the phone number.");
                }
            }
               
            connectToDatabase();
 
            pStatement = connection.prepareStatement(query);
            // set parameter values
            pStatement.setString(1, fullName);
            pStatement.setString(2, email);
            pStatement.setString(3, address);
            pStatement.setInt(4, phone);
            
            
            int check = pStatement.executeUpdate();    
            if (check > 0) {
                System.out.print("\nCustomer " + fullName + " successfully added! ");
                
                try {
                    String custIdquery = "Select cust_id, email from customer where fullname = ? and email = ?";
                    
                    
                    pStatement = connection.prepareStatement(custIdquery);
                    pStatement.setString(1, fullName);
                    pStatement.setString(2, email);
                    result = pStatement.executeQuery();
                    while(result.next()){
                        int cust_id = result.getInt("cust_id");
                        System.out.println("with Customer ID: "+cust_id);
                        custId = cust_id;
                    } 
                } catch (SQLException e) {
                    System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
                }
                
            } else {
                System.out.println("Query failed.");
            }
            
            closeAllConnections();
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
        return custId;
    }
    
    
    public void displayAllCustomer(){
        final String QUERY = "SELECT * FROM customer";
        try {
            connectToDatabase();
            state = connection.createStatement();
            result = state.executeQuery(    QUERY);
            System.out.format("+--------------------------------------------------------------------------------------------------+%n");
            System.out.println(String.format("|  %-3s %-20s %-25s %-10s %-25s %-7s |", "ID", "Fullname", "Email", "Phone", "Address", "Archive"));
            System.out.format("+--------------------------------------------------------------------------------------------------+%n");
        
            while(result.next()){
                int id = result.getInt("cust_id");
                String name = result.getString("fullname");
                String email = result.getString("email");
                int phone = result.getInt("phone");
                String address = result.getString("address");
                int archived = result.getInt("archived");
                
                System.out.format("|  %-3d %-20s %-25s %-10d %-25s %-7s |%n", id, name, email, phone, address, archived);
            }
            closeAllConnections();
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
    }

    
    public int verifyCustomer(){
        displayAllCustomer();
        System.out.format("+--------------------------------------------------------------------------------------------------+%n");
        try {
            String verifyCustQuery = "SELECT CUST_ID, FULLNAME, EMAIL FROM CUSTOMER WHERE FULLNAME = ? AND EMAIL = ? AND ARCHIVED = 0";
            
            System.out.print("ENTER CUSTOMER'S FULL NAME: ");//will fetch from database fullname
            String customerFullname = sc.nextLine();
            System.out.print("ENTER CUSTOMER'S EMAIL: ");
            String customerEmail = sc.nextLine();
            
            connectToDatabase();
            pStatement = connection.prepareStatement(verifyCustQuery);
            // set parameter values
            pStatement.setString(1, customerFullname);
            pStatement.setString(2, customerEmail);
            result = pStatement.executeQuery();
            
            int cust_id = -1;
            
            while(result.next()){
                cust_id = result.getInt("cust_id");
                System.out.println("You are verified customer with ID: "+cust_id);
                }
           
            closeAllConnections();

            if (cust_id < 1) {
                System.out.println("There is no record of a customer with name " + customerFullname + " and email " + customerEmail);
                System.out.println("Please enter valid information.");
            }
            return cust_id;
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
            return -1;
        } 
    }
    
    
    public void updateCustomer(){
        try {
            Scanner sc = new Scanner(System.in);
            
            displayAllCustomer();
            System.out.println("| (\u001B[31mX\u001B[0m) CANCEL/RETURN                                                                                |");
            System.out.format("+----------------------------------------------------------==-------------------------------------+%n");
            
            connectToDatabase();
            
            System.out.print("Enter customer ID you want to update: ");
            String userInput = sc.nextLine();
            
            if ("X".equalsIgnoreCase(userInput)) {
                // Cancel/Return to the previous menu
                custInfo.customerInfo();
            }else{
                
                System.out.print("Enter new fullname: ");
                String newFullname = sc.nextLine();
                
                System.out.print("Enter new email: ");
                String newEmail = sc.nextLine();
                
                System.out.print("Enter new phone: ");
                int newPhone = sc.nextInt();
                sc.nextLine();
                
                System.out.print("Enter new address: ");
                String newAddress = sc.nextLine();
                
                System.out.print("Enter archived: ");
                int newArchived = sc.nextInt();
                // Update the description in the database
                String updateQuery = "UPDATE customer SET fullname = ?, email = ?, phone = ?, address = ?, archived = ? WHERE cust_id = ?";
                
                pStatement = connection.prepareStatement(updateQuery);
                pStatement.setString(1, newFullname);
                pStatement.setString(2, newEmail);
                pStatement.setInt(3, newPhone);
                pStatement.setString(4, newAddress);
                pStatement.setInt(5, newArchived);
                pStatement.setInt(6, Integer.parseInt(userInput));
                int updatedRows = pStatement.executeUpdate();

                if (updatedRows > 0) {
                    System.out.println("Customer updated successfully!");
                    
                } else {
                    System.out.println("Failed to update service. Service ID may not exist.");
                }
            }
            // Close resources
            closeAllConnections();
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
    }
}
