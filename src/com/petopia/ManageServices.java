
package com.petopia;

import com.petopiables.Functions;
import com.petopiables.PetopiaHeader;
import java.util.Scanner;


public class ManageServices extends PetopiaHeader{
    
    void manageServices(){
        //instantiations
        Scanner sc = new Scanner(System.in);
        Services services = new Services();
        LogIn logIn = new LogIn();
        CustomerInfo customerInfo = new CustomerInfo();
        PetInfo petInfo = new PetInfo();
        SelectGroomingService selectGroomingService = new SelectGroomingService();
        AppointmentInfo apptInfo = new AppointmentInfo();
        Functions fx = new Functions();
        
        System.out.println(logIn.qLevel + " - " + logIn.qUsername);
        petopiaHeader();
        
        System.out.format("|            MANAGE SERVICES            |%n");
        System.out.format("+---------------------------------------+%n");
        padThreeBars();
        System.out.format("|     (\u001B[36m1\u001B[0m) ENTER CUSTOMER INFO           |%n");
        System.out.format("|     (\u001B[36m2\u001B[0m) ENTER PET INFO                |%n");
        System.out.format("|     (\u001B[36m3\u001B[0m) SELECT GROOMING SERVICE       |%n");      
        System.out.format("|     (\u001B[36m4\u001B[0m) ENTER APPOINTMENT INFO        |%n");
        System.out.format("|     (\u001B[36m5\u001B[0m) CANCEL/RETURN                 |%n");
        System.out.format("|                                       |%n");
        
        padThreeBars();
        System.out.format("+---------------------------------------+%n");
               
        try {
           System.out.print("Enter a service: ");
            String input = sc.nextLine();
            if (fx.isNumber(input)) {
                int choice = Integer.parseInt(input);
                if (choice > 0 && choice < 6){  
                        if (choice == 1){
                            fx.cleanConsoleTwenty(); // clears the upper console 20 lines
                            customerInfo.customerInfo();
                        } else if (choice == 2){
                            fx.cleanConsoleTwenty();
                            petInfo.petInfo();
                        } else if (choice == 3) {
                            fx.cleanConsoleTwenty();
                            selectGroomingService.selectGroomingService();
                        } else if (choice == 4) {
                            fx.cleanConsoleTwenty();
                           apptInfo.appointmentInfo();
                        } else {//cancel- back to Services 
                            fx.cleanConsoleTwenty();
                            services.services(logIn.qUsername, logIn.qLevel);
                        }
                } else {
                    fx.errorMessage("1 - 5", this::manageServices);
                }
            } else {
                fx.errorMessage("1 - 5", this::manageServices);
            } 
       } catch (NumberFormatException  e) {
           System.out.println("NumberFormatException: \u001B[31m" + e + "\u001B[0m");
           fx.errorMessage("1 - 5", this::manageServices);           
       }
    }
}
