package com.petopia.api;

import com.petopia.db.DatabaseConnections;
import com.petopiables.Appointment;
import com.petopiables.Service;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class AppointmentQuery extends DatabaseConnections{
       // Create a new instance of the Appointment class
       Appointment appt = new Appointment();
       // Create a new instance of the Service class
       Service service = new Service();
   
    public void displayAppointmentsUsingInnerJoin(){
        SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd");
        String query = "Select * from appointment "           
                + "inner join customer ON appointment.cust_id = customer.cust_id "
                + "inner join service on appointment.serv_id = service.serv_id " 
                + "where status = 0";
        
        try {
            connectToDatabase();
            pStatement = connection.prepareStatement(query);
            result = pStatement.executeQuery(query);
             System.out.printf("%-10s %-30s %-20s %-20s %-10s %n",
                    "ID", "SERVICE", "CUSTOMER" ,"APPOINTMENT DATE", "TOTAL COST");
          
             while(result.next()){
                int appointment_id = result.getInt("appointment_id");
                String serviceDesc = result.getString("desc");
                String customerName = result.getString("fullname"); 
                double totalCost = result.getDouble("total_cost");
                Date date = result.getDate("appointment_date");
                
                //("2023-10-01")
                String apptDate = sdf.format(date);
                
                // Set the ID for the appointment object
                appt.setId(appointment_id);
                
                 // Set the service description for the appointment object
                appt.setServDesc(serviceDesc);
                
               // Set the customer name for the appointment object
                appt.setCustomerName(customerName);
                
                // Set the total cost for the appointment object
                appt.setTotalCost(totalCost);
               
                // Set the appointment date for the appointment object
                appt.setDate(apptDate);
                
                //// Retrieve the appointment ID,service description,customer name, appointment date, and the total cost of appointment
                 System.out.format("%-10d %-30s %-20s %-20s %.2f %n",  
                 appt.getId(), appt.getServDesc(), appt.getCustomerName(), appt.getDate(), appt.getTotalCost()); 
    
                
            }
            closeAllConnections();
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
    }

 
    //Displays the appointments using an inner join on the appointment, customer, and service tables.
    //Only displays the appointments with a status of 0 (assuming 0 represents active or pending appointments).
     public void addNewAppointment(int custId, String appointmentDate, double totalCost, int status) {
       
       String query = "INSERT INTO appointment (cust_id, appointment_date, total_cost,status) VALUES (?, ?, ?, ?)";
       String selectNameQuery = "SELECT fullname from customer where cust_id = ?";
       
          try {
            connectToDatabase();

            // prepareStatement
            pStatement = connection.prepareStatement(query);       
            pStatement.setInt(1, custId);;
            pStatement.setString(2, appointmentDate);
            pStatement.setDouble(3, totalCost);
            pStatement.setInt(4, status);
            
            pStatement = connection.prepareStatement(selectNameQuery);          
            pStatement.setInt(1, custId);
                   
            result = pStatement.executeQuery();


            if (result.next()) {
               String fullname = result.getString("fullname");
            System.out.println("Appointment to customer " + fullname + " is scuccessffully added!");
            } else {
                System.out.println("Query failed.");
            }
            
            closeAllConnections();
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
      }

     
    public void displayServiceDescription() {
        String query = "SELECT * FROM service";
            
            try {
            connectToDatabase();
            pStatement = connection.prepareStatement(query);
            result = pStatement.executeQuery(query);
                    
            
          System.out.printf("%-10s %-30s %-10s %n",  
                 "SERVICE ID", "DESCTRIPTION", "PRICE");
           
            while(result.next()){
                int servId = result.getInt("serv_id");
                String serviceDesc = result.getString("desc"); 
                double price = result.getDouble("price");
                
                //set the  ID for the serivce object
                service.setId(servId);
                //set the  description for the service object
                service.setDesc(serviceDesc);
                //set the  price for the service object
                service.setPrice(price);
                
                System.out.format(" %-7d  %-30s  %.2f %n",  
                       service.getId(), service.getDesc(), service.getPrice());
                           
            }
                        
            closeAllConnections();
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
    }
       
       
    //Retreives the price of a service based on the provided service ID.
    public double getServicePrice(int serviceId) {
        double price = 0.0;

        try {
            connectToDatabase();
            String query = "SELECT price FROM service WHERE serv_id = ?";
            pStatement  = connection.prepareStatement(query);
            pStatement.setInt(1, serviceId);
            result = pStatement.executeQuery();

            if (result.next()) {
                price = result.getDouble("price");
            }
            
            closeAllConnections();
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
        
        //return The price of the service as a double value
        return price;
    }
    
    
    public void updateTotalAmouintService(double total, int apptId){
        String query = "UPDATE appointment SET total_cost = ?  where appointment_id = ?";
        
        try {
            connectToDatabase();
            pStatement = connection.prepareStatement(query);      
            pStatement.setDouble(1, total);
            pStatement.setInt(2, apptId);
     
            pStatement.executeUpdate();  //modifies the database
            
            System.out.println("Appoinment " + apptId + " total cost upodated succesffully!");
             
            closeAllConnections();
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }     
    }
    
    //Updates the total cost of a service for a specific appointment in the database.
    public void cancelAppointment(int id){
        
         String query = "UPDATE appointment SET appointment.status = 1 where appointment.appointment_id = ?";
         
         try {  
          
           connectToDatabase();
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, id);
            
            pStatement.executeUpdate();
            
            System.out.println("Appointment ID  " + id + " is successfully deleted");
           
            closeAllConnections();
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
    }
}
