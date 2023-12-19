
package com.petopia;

import com.petopia.api.CustomerQuery;
import com.petopiables.PetopiaHeader;
import java.util.Scanner;

public class CustomerInfo extends PetopiaHeader{
    public void customerInfo(){
        //instantiations
        Scanner sc = new Scanner(System.in);
        ManageServices manageServices = new ManageServices();
        CustomerQuery customerQuery = new CustomerQuery();
        petopiaHeader();
        
        System.out.format("|          CUSTOMER INFORMATION         |%n");
        System.out.format("+---------------------------------------+%n");
        System.out.format("|                                       |%n");
        System.out.format("|                                       |%n");
        System.out.format("|    (\u001B[36m1\u001B[0m) ENTER NEW CUSTOMER             |%n");
        System.out.format("|    (\u001B[36m2\u001B[0m) VERIFY CUSTOMER                |%n");
        System.out.format("|    (\u001B[36m3\u001B[0m) UPDATE/SOFT DELETE CUSTOMER    |%n");
        System.out.format("|    (\u001B[36m4\u001B[0m) CANCEL/RETURN                  |%n");
        padThreeBars();
        System.out.format("+---------------------------------------+%n");
        
        System.out.print("Enter a service: ");
        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                // INSERT INTO CUSTOMER TABLE VALUES...
                customerQuery.addCustomer();
                customerInfo();
                break;
            case 2:
                customerQuery.verifyCustomer();
                customerInfo();
                break;
            case 3:
                // UPDATE CUSTOMER () SET VALUES ()
                // SOFT DELETE ONLY.
                customerQuery.updateCustomer();
                customerInfo();
                break;
            case 4:
                manageServices.manageServices();
            default:
                // Invalid input
                System.out.println("Invalid input. Please enter a valid option (1-4).");
                customerInfo();
                break;
        }         
    }
}
