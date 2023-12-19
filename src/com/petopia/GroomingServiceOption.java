
package com.petopia;

import com.petopiables.PetopiaHeader;
import java.util.Scanner;


public class GroomingServiceOption extends PetopiaHeader{
    public void groomingServiceOption(){
        petopiaHeader();
        SelectGroomingService selectGroomingService = new SelectGroomingService();
        ManageServices manageServices = new ManageServices();
        UpdateService updateGroomingService = new UpdateService();
        
        Scanner sc = new Scanner(System.in);
        System.out.format("|        GROOMING SERVICE OPTION        |%n");
        System.out.format("+---------------------------------------+%n");
        System.out.format("|      (\u001B[36m1\u001B[0m) UPDATE SERVICE RECORDS       |%n");
        System.out.format("|      (\u001B[36m2\u001B[0m) PROCESS GROOMING SERVICE     |%n");
        System.out.format("|      (\u001B[36m3\u001B[0m) CANCEL/RETURN                |%n");
        System.out.format("+---------------------------------------+%n");
        
        
        System.out.print("Enter a service: ");
        int choice = sc.nextInt();
            
        switch (choice) {
            case 1:
                updateGroomingService.updateGroomingService();
                groomingServiceOption();
                break;
            case 2:
                // Handle the "Process Grooming Service" option
                selectGroomingService.selectGroomingService();
                groomingServiceOption();
                break;
            case 3:
                manageServices.manageServices(); // Cancel/Return
                break;
            default:
                System.out.println("Invalid input. Please enter a valid option (1-3).");
                groomingServiceOption();
                break; 
            }
    }
}