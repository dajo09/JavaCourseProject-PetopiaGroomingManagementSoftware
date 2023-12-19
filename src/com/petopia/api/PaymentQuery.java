
package com.petopia.api;

import com.petopia.SelectGroomingService;
import com.petopia.db.DatabaseConnections;
import com.petopiables.PetopiaHeader;
import java.sql.SQLException;
import java.util.Scanner;

public class PaymentQuery extends DatabaseConnections{
    public int displayPaymentMethods(){
        PetopiaHeader petopiaHeader = new PetopiaHeader();
        petopiaHeader.displayPaymentMethodsHeader();
        SelectGroomingService selectGroomingService = new SelectGroomingService();
        Scanner sc = new Scanner(System.in);
        int selectedPaymentTypeId = 0;
        try {
            connectToDatabase();
            String paymentRecordsQuery = "SELECT PAY_ID, `DESC` FROM PAYMENT WHERE AVAILABLE = 1";
            System.out.format("| %s   %-25s    |%n", "PAY ID", "DESCRIPTION");
            pStatement = connection.prepareStatement(paymentRecordsQuery);
            result = pStatement.executeQuery();

            // Display services dynamically based on the database
            int option = 1;
            while (result.next()) {
                int payId = result.getInt("pay_id");
                String desc = result.getString("desc");
                
                System.out.format("| %-6d   %-25s    |%n", payId, desc);
                option++;
            }
            
            System.out.format("|(6)       CANCEL/RETURN                |%n");
            System.out.format("+---------------------------------------+%n");
            
            System.out.print("Select Payment Type: ");
            int paymentTypeId = sc.nextInt();
            
            if(paymentTypeId >=1 && paymentTypeId < option){
                selectedPaymentTypeId = paymentTypeId;
            }else if (paymentTypeId == 6) {
                // If user selects option 6, call the selectGroomingService method
                
                selectGroomingService.selectGroomingService();
            }else {
                System.out.println("Invalid payment type selection.");
            }
            closeAllConnections();
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
        return selectedPaymentTypeId;
    }
    
    
    public void addReceipt(String date, int custId, int petId, int paymentTypeId, float total, int empId) {
        try {
            connectToDatabase();
            String insertReceiptQuery = "INSERT INTO receipt (date, cust_id, pet_id, pay_id, total, emp_id) VALUES (?, ?, ?, ?, ?, ?)";
            pStatement = connection.prepareStatement(insertReceiptQuery);
            pStatement.setString(1, date); // Use the date field
            pStatement.setInt(2, custId);
            pStatement.setInt(3, petId);
            pStatement.setInt(4, paymentTypeId);
            pStatement.setFloat(5, total);
            pStatement.setInt(6, empId);
                        
            int rowsInserted = pStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Receipt added successfully!");
            } else {
                System.out.println("Failed to add receipt.");
            }
             closeAllConnections();
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
    }
    
    
    public float retrieveServicePrice(int serv_id){
        float price = 0; // Initialize the price to a default value
        try {
            connectToDatabase();
            String query = "SELECT PRICE FROM SERVICE WHERE SERV_ID = ?";
                        
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, serv_id);
            result = pStatement.executeQuery();
            
            while (result.next()) {
                price = result.getFloat("price");
            }
            closeAllConnections();
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }           
        return price;
    }
}

