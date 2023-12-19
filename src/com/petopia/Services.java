
package com.petopia;

import com.petopiables.PetopiaHeader;
import com.petopiables.Functions;
import com.petopia.db.DisconnectDatabase;
import java.util.Scanner;

public class Services extends PetopiaHeader{
    Functions fx = new Functions();
    
    public void services(String name, String accessLevel){
       //instantiations
        Scanner sc = new Scanner(System.in);
        ManageServices manageServices = new ManageServices();
        GenerateReceipt generateReceipt = new GenerateReceipt();
        EmployeeInfo employeeInfo = new EmployeeInfo();
        LogIn logIn = new LogIn();
        DisconnectDatabase disconnectDb = new DisconnectDatabase();

        System.out.println(accessLevel + " - " + name);
        petopiaHeader();
        padThreeBars();
        System.out.format("|       (\u001B[36m0\u001B[0m) EXIT SYSTEM                 |%n");
        System.out.format("|       (\u001B[36m1\u001B[0m) LOG-OUT                     |%n");
        System.out.format("|       (\u001B[36m2\u001B[0m) MANAGE SERVICES             |%n");
        System.out.format("|       (\u001B[36m3\u001B[0m) GENERATE RECEIPTS           |%n");
        if (accessLevel.equals("Admin")){//table access.level = Admin
            System.out.format("|       (\u001B[36m4\u001B[0m) MANAGE SYSTEM USERS         |%n");
        }
        System.out.format("|                                       |%n");   
        padThreeBars();
        System.out.format("+---------------------------------------+%n");
        
        try {
            System.out.print("Welcome " + name + "!\nPlease enter a field: ");
            String choiceInput = sc.nextLine();
            if (fx.isNumber(choiceInput)){
                int choice = Integer.parseInt(choiceInput);  
                if (choice < 5) { // choices are 0 - 4 only. more than this is an error
                    if (choice == 0) {
                        disconnectDb.disconnectFromDatabase();
                        System.out.println("\n\n\n\n\n\nExiting Petopia Management System."+
                                "\nGoodbye " + name + "!\n               ......\n");
                        System.exit(0); // terminates JVM
                    } else if (choice == 1){// log-in window appears for next user to log-in
                        fx.cleanConsoleTwenty();
                        logIn.LogIn();
                    } else if (choice == 2){
                        fx.cleanConsoleTwenty();
                        manageServices.manageServices();    
                    } else if (choice == 3){
                         fx.cleanConsoleTwenty();
                        generateReceipt.generateReceipt();
                    } else  {
                        fx.cleanConsoleTwenty();
                        employeeInfo.employeeInfo();
                    }
                } else {   
                    fx.errorMessage("0 - 3", () -> services(name, accessLevel));
                    }
            } else {
                fx.errorMessage("0 - 3", () -> services(name, accessLevel));
            }
        } catch (NumberFormatException e) {
             System.out.println("NumberFormatException: \u001B[31m" + e + "\u001B[0m");
            fx.errorMessage("0 - 3", () -> services(name, accessLevel));
        }
   }   
}
