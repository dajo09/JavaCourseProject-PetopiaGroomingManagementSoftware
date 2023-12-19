
package com.petopia;

import com.petopia.api.ServiceQuery;
import com.petopia.db.DatabaseConnections;
import com.petopiables.PetopiaHeader;
import java.util.Scanner;

public class UpdateService extends DatabaseConnections{
    
    public void updateGroomingService() {
        try {
            Scanner sc = new Scanner(System.in);
            ServiceQuery serviceQuery = new ServiceQuery();
            GroomingServiceOption groomingServiceOption = new GroomingServiceOption();
            PetopiaHeader displayHeader = new PetopiaHeader();

            // Display available services for selection
            connectToDatabase();
            String selectQuery = "SELECT * FROM service";
            pStatement = connection.prepareStatement(selectQuery);
            result = pStatement.executeQuery();

            serviceQuery.displayAllServiceRecords();            
            displayHeader.cancelOrReturnHeaderLong();
            
            System.out.print("Enter the service ID you want to update: ");
            int choice = sc.nextInt();

            if (choice == 6) {
                // Cancel/Return to the previous menu
                groomingServiceOption.groomingServiceOption();
            }else if(choice >=1){
                sc.nextLine();
                System.out.print("Enter the new description: ");
                String newDescription = sc.nextLine();
            
                System.out.print("Enter the new price: ");
                Float newPrice = sc.nextFloat();
                sc.nextLine();
            
                System.out.print("Enter the new availability status: ");
                int newAvailable = sc.nextInt();
            
                // Update the description in the database
                String updateQuery = "UPDATE service SET `desc` = ?, price = ?, available = ? WHERE serv_id = ?";
                pStatement = connection.prepareStatement(updateQuery);
                pStatement.setString(1, newDescription);
                pStatement.setFloat(2, newPrice);
                pStatement.setInt(3, newAvailable);
                pStatement.setInt(4, choice);
                int updatedRows = pStatement.executeUpdate();

                if (updatedRows > 0) {
                    System.out.println("Service updated successfully!");
                } else {
                    System.out.println("Failed to update service. Service ID may not exist.");
                }
            }else{
                System.out.println("Invalid input. Please enter a valid option (1-6).");
                updateGroomingService();
            }
            // Close resources
            closeAllConnections();
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }
}
