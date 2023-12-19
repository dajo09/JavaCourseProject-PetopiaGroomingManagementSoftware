
package com.petopia.api;

import com.petopia.db.DatabaseConnections;
import com.petopiables.PetopiaHeader;
import java.sql.SQLException;

public class ServiceQuery extends DatabaseConnections {      
    public void displayAllServiceRecords(){
        PetopiaHeader displayHeader = new PetopiaHeader();
        displayHeader.displayServiceDetailsHeader();
        try {
            String query = "Select * from service";
            connectToDatabase();
            state = connection.createStatement();
            result = state.executeQuery(query);

            displayHeader.displayAllServiceColumnsHeader();
          
            while (result.next()) {
                int servId = result.getInt("serv_id");
                String description = result.getString("desc");
                float price = result.getFloat("price");
                int available = result.getInt("available");

                // Print using String.format to align columns
                System.out.println(String.format("| (%s) %-25s %-10s  %-14s |",servId, description, price, available));
                System.out.format("+----------------------------------------------------------+%n");
                }
            // Close the connection
            closeAllConnections();
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
    }
}
