package com.petopia;

import com.petopia.api.CustomerQuery;
import com.petopia.api.PaymentQuery;
import com.petopia.api.PetQuery;
import com.petopia.db.DatabaseConnections;
import com.petopiables.PetopiaHeader;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SelectGroomingService extends DatabaseConnections {
    public void selectGroomingService() {
        // Instantiations
        Scanner sc = new Scanner(System.in);
        GroomingServiceOption groomingServiceOption = new GroomingServiceOption();       
        CustomerQuery customerQuery = new CustomerQuery();
        PetQuery petQuery = new PetQuery();
        PaymentQuery paymentQuery = new PaymentQuery();
        ManageServices manageServices = new ManageServices();
        PetopiaHeader petopiaHeader = new PetopiaHeader();
        petopiaHeader.petopiaHeader();
        LogIn login = new LogIn();
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(currentDate);
              
        List<Integer> validServiceIDs = new ArrayList<>();

        try {
            connectToDatabase();
            String query = "SELECT SERV_ID, `DESC`, PRICE FROM SERVICE WHERE AVAILABLE = 1";
            pStatement = connection.prepareStatement(query);
            result = pStatement.executeQuery();
            
            petopiaHeader.availableServiceHeader();
            petopiaHeader.displayAvailableServiceHeader();

            // Display services dynamically based on the database
            int option = 2;
            System.out.format("|                                       |%n");
            System.out.format("|                                       |%n");
            while (result.next()) {
                int servId = result.getInt("serv_id");
                String desc = result.getString("desc");
                float price = result.getFloat("price");
                System.out.format("| %s  %-25s %-6s |%n", "(\u001B[36m" + servId + "\u001B[0m)", desc, price);
                
                validServiceIDs.add(servId);
                option++;
            }

            System.out.format("| (\u001B[36m6\u001B[0m)  CANCEL/RETURN                    |%n");
            petopiaHeader.padThreeBars();
            System.out.format("+---------------------------------------+%n");

            System.out.print("Enter a service: ");
            int choice = sc.nextInt();
            sc.nextLine();
            
            if (choice >= 1 && choice < option && validServiceIDs.contains(choice)) {
                int custId = -1;    
                int petId = -1;
                int payId;
                int empId;
                int serviceId = choice;
                float price = paymentQuery.retrieveServicePrice(serviceId);
                empId = login.getCurrentUserId();

                System.out.print("ARE YOU A NEW CUSTOMER?(yes/y/no/n): ");
                String userInput = sc.nextLine().toLowerCase();
                if (userInput.equals("yes") || userInput.equals("y")){
                    custId = customerQuery.addCustomer();
                    System.out.println("");        
                    System.out.print("ENTER PET NAME: ");
                    String petName = sc.nextLine();
            
                    petQuery.displayPetType();
                    System.out.print("CHOOSE A PET ID TYPE: ");  
                    
                    int type;
                    try {
                        type = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid integer for the pet type.");
                        // Handle the error or exit gracefully
                        return;
                    }
                    int age;

                    while (true) {
                        System.out.print("AGE: ");

                        // Check if the next input is an integer
                        if (sc.hasNextInt()) {
                            age = sc.nextInt();
                            // Consume the newline character
                            sc.nextLine();
                            // Check if the age is within a valid range (adjust the range as needed)
                            if (age >= 0 && age <= 30) {
                                // Valid age, exit the loop
                                break;
                            } else {
                                System.out.println("Invalid age. Please enter a valid age.");
                            }
                        } else {
                            // Invalid input (not an integer)
                            System.out.println("Invalid input. Please enter a valid integer for the age.");
                            sc.nextLine(); // Consume the invalid input to prevent an infinite loop
                        }
                    }
                    
                    System.out.print("BREED: ");
                    String breed = sc.nextLine();
            
                    System.out.print("OWNER ID: "+custId);
                                
                    int archived = 0;
                    System.out.println("\n");
            
                    petQuery.addNewPet(petName, type, age, breed, custId, archived);
                    petId = petQuery.displayPetByDogOwners(custId);
                    
                    payId = paymentQuery.displayPaymentMethods();
                    
                    paymentQuery.addReceipt(date, custId, petId, payId, price, empId);
                    System.out.println("");
                    manageServices.manageServices();
                } else if (userInput.equals("no") || userInput.equals("n")) {
                    custId = customerQuery.verifyCustomer();
                    if(custId == -1){
                        custId = customerQuery.verifyCustomer();
                    } else{
                        System.out.print("NEW PET?:(yes/y/no/n) ");
                        userInput = sc.nextLine();
                        if(userInput.equals("yes") || userInput.equals("y")){
                            // INSERT INTO PET VALUES....
                            // PETID WILL BE AUTOGENERATED
                            System.out.print("\nENTER PET NAME: ");
                            String petName = sc.nextLine();
                            
                            petQuery.displayPetType();
                            System.out.print("CHOOSE A PET ID TYPE: ");  
            
                            int type;
                            try {
                                type = Integer.parseInt(sc.nextLine());
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please enter a valid integer for the pet type.");
                                // Handle the error or exit gracefully
                                return;
                            }
                            int age;

                            while (true) {
                                System.out.print("AGE: ");

                                // Check if the next input is an integer
                                if (sc.hasNextInt()) {
                                    age = sc.nextInt();
                                    // Consume the newline character
                                    sc.nextLine();
                                    // Check if the age is within a valid range (adjust the range as needed)
                                    if (age >= 0 && age <= 30) {
                                        // Valid age, exit the loop
                                        break;
                                    } else {
                                        System.out.println("Invalid age. Please enter a valid age.");
                                    }
                                } else {
                                    // Invalid input (not an integer)
                                    System.out.println("Invalid input. Please enter a valid integer for the age.");
                                    sc.nextLine(); // Consume the invalid input to prevent an infinite loop
                                }
                            }
                            
                            System.out.print("BREED: ");
                            String breed = sc.nextLine();
            
                            System.out.print("OWNER ID: "+custId);
            
                            int archived = 0;
                            System.out.println("\n");
            
                            // ENTER ALL FIELDS IN THE DATABASE
                            petQuery.addNewPet(petName, type, age, breed, custId, archived);
                            petId = petQuery.displayPetByDogOwners(custId);
                                                
                            payId = paymentQuery.displayPaymentMethods();  
                        
                            paymentQuery.addReceipt(date, custId, petId, payId, price, empId);
                            System.out.println("");
                            manageServices.manageServices();
                    }else{
                        petId = petQuery.displayPetByDogOwners(custId);
                        if (petId == 0){
                            
                            System.out.println("You don't have any Pet registered in our system!");
                            System.out.print("Would you like to register new pet? (yes/y/no/n): ");
                            userInput = sc.nextLine();
                            if(userInput.equals("yes") || userInput.equals("y")){
                                System.out.print("\nENTER PET NAME: ");
                                String petName = sc.nextLine();
                            
                            petQuery.displayPetType();
                            System.out.print("CHOOSE A PET ID TYPE: ");  
            
                            int type;
                            try {
                                type = Integer.parseInt(sc.nextLine());
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please enter a valid integer for the pet type.");
                                // Handle the error or exit gracefully
                                return;
                            }
                            int age;

                            while (true) {
                                System.out.print("AGE: ");

                                // Check if the next input is an integer
                                if (sc.hasNextInt()) {
                                    age = sc.nextInt();
                                    // Consume the newline character
                                    sc.nextLine();
                                    // Check if the age is within a valid range (adjust the range as needed)
                                    if (age >= 0 && age <= 30) {
                                        // Valid age, exit the loop
                                        break;
                                    } else {
                                        System.out.println("Invalid age. Please enter a valid age.");
                                    }
                                } else {
                                    // Invalid input (not an integer)
                                    System.out.println("Invalid input. Please enter a valid integer for the age.");
                                    sc.nextLine(); // Consume the invalid input to prevent an infinite loop
                                }
                            }
                            
                            System.out.print("BREED: ");
                            String breed = sc.nextLine();
            
                            System.out.print("OWNER ID: "+custId);
            
                            int archived = 0;
                            System.out.println("\n");
            
                            // ENTER ALL FIELDS IN THE DATABASE
                            petQuery.addNewPet(petName, type, age, breed, custId, archived);
                            petId = petQuery.displayPetByDogOwners(custId);
                            payId = paymentQuery.displayPaymentMethods();
                            paymentQuery.addReceipt(date, custId, petId, payId, price, empId);
                            System.out.println("");
                            manageServices.manageServices();
                            } else{
                                System.out.println("Service not availed.");
                                manageServices.manageServices();
                            }
                        } else {
                            System.out.print("\nPet id: ");
                            petId = sc.nextInt();
                            
                            payId = paymentQuery.displayPaymentMethods();
                            paymentQuery.addReceipt(date, custId, petId, payId, price, empId);
                            System.out.println("");
                            manageServices.manageServices();
                        }
                    }
                    }
                    } else {
                        System.out.println("Invalid choice. Service not availed.");
                    }
                } else if(choice == 6){
                    groomingServiceOption.groomingServiceOption();
                } else {
                    System.out.println("Invalid Input! Enter a valid service ID.");
                    selectGroomingService();
                }
            // Close database resources
            closeAllConnections();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
}

