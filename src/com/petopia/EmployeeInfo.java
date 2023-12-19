
package com.petopia;

import com.petopia.api.EmployeeQuery;
import com.petopiables.PetopiaHeader;
import com.petopiables.Functions;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class EmployeeInfo extends PetopiaHeader{
    public static int empId;
    Scanner sc = new Scanner(System.in);
    EmployeeQuery empQuery = new EmployeeQuery();
    Functions fx = new Functions();
    GenerateReceipt genRec = new GenerateReceipt();
    
    public void employeeInfo() {
        //instantiations
        Services services = new Services();
        LogIn logIn = new LogIn();
        
        System.out.println(logIn.qLevel + " - " + logIn.qUsername);
        petopiaHeader();
        
        System.out.format("|         EMPLOYEE INFORMATION          |%n");
        System.out.format("+---------------------------------------+%n");
        System.out.format("|                                       |%n");   
        System.out.format("|       (\u001B[36m1\u001B[0m) NEW EMPLOYEE                |%n");
        System.out.format("|       (\u001B[36m2\u001B[0m) UPDATE EMPLOYEE             |%n");
        System.out.format("|       (\u001B[36m3\u001B[0m) DELETE EMPLOYEE             |%n");
        System.out.format("|       (\u001B[36m4\u001B[0m) ADD EMPLOYEE ACCESS         |%n");
        System.out.format("|       (\u001B[36m5\u001B[0m) DELETE EMPLOYEE ACCESS      |%n");
        System.out.format("|       (\u001B[36m6\u001B[0m) VIEW EMPLOYEE TABLE         |%n");
        System.out.format("|       (\u001B[36m7\u001B[0m) VIEW ACCESS TABLE           |%n");
        System.out.format("|       (\u001B[36m8\u001B[0m) CANCEL/RETURN               |%n");
        System.out.format("|                                       |%n");   
        System.out.format("+---------------------------------------+%n");
        
        try {
            System.out.print("Enter a field: ");
            String choiceInput = sc.nextLine();
            if (fx.isNumber(choiceInput)){
                int choice = Integer.parseInt(choiceInput);
                if (choice > 0 && choice < 9) {   
                    if (choice == 1){
                        System.out.print("Type '\u001B[31mX\u001B[0m' to CANCEL\nEnter RETURN or type any character to continue: ");
                        String input = sc.nextLine();

                        if (input.equalsIgnoreCase("X")) {
                            fx.xIsInput(this::employeeInfo);                    
                        } else {
                            fx.cleanConsoleTwenty();
                            System.out.format("\n+---------------------------------------+%n");
                            System.out.format("|        NEW EMPLOYEE INFORMATION       |%n");
                            System.out.format("+---------------------------------------+%n");
                            System.out.print("\tFULL NAME: ");
                            String fullname = sc.nextLine();
                            System.out.print("\tEMAIL: ");
                            String email = sc.nextLine();
                            System.out.print("\tADDRESS: ");
                            String address = sc.nextLine();
                            System.out.print("\tPOSITION: ");
                            String position = sc.nextLine();
                            // ARCHIVED IS DEFAULT TO 0 FOR NEW EMPLOYEE

                            empQuery.addNewEmployee(fullname, email, address, position);
                            fx.xToCancel(this::employeeInfo);
                            }
                        }else if (choice == 2) {//update emp info              
                            this.searchName();
                            fx.xToCancel(this::employeeInfo);
                        } else if(choice == 3){//DELETE EMPLOYEE INFO FROM EMPLOYEE AND ACCESS TABLE
                            this.deleteEmployee();
                        } else if (choice == 4) {//add emp access
                            this.addEmpAccess();
                        } else if (choice == 5){//delete emp access
                            fx.cleanConsoleTwenty();
                            this.delAccess();
                        } else if (choice==6){
                            fx.cleanConsoleTwenty();
                            System.out.format("+-------------------------------------------------------"
                                    + "-------------------------------------------------------------+%n");
                            System.out.format("|%35s %28s                                                    |%n", "", "VIEW EMPLOYEE ");
                            empQuery.displayAllEmployee();//view employee table
                            fx.xToCancel(this::employeeInfo);
                        } else if (choice == 7){    
                            fx.cleanConsoleTwenty();
                            System.out.format("+---------------------------------------------------------+%n");
                            System.out.format("|                      VIEW ACCESS                        |%n");
                            empQuery.displayAllAccess();
                            this.viewAccess();
                        }else  {//cancel/return
                            fx.cleanConsoleTwenty();
                            services.services(logIn.qUsername, logIn.qLevel);
                        }
                } else {
                    fx.errorMessage("1 - 8", this::employeeInfo);
                }
            } else {
                  fx.errorMessage("1 - 8", this::employeeInfo);
            } 
       } catch (NumberFormatException e) {
           System.out.println("NumberFormatException: \u001B[31m" + e + "\u001B[0m");
           fx.errorMessage("1 - 8", this::employeeInfo);
       }
    }
    
    
    
    void delFormat() {
        genRec.empLineFormat();
        System.out.format("|%35s %28s                                                    |%n",
                                    "", "DELETE EMPLOYEE");
    }

    
    void deleteEmployee(){
        fx.cleanConsoleTwenty();
        this.delFormat();
        empQuery.displayAllEmployee();//view employee table
        do {
            try {
                System.out.print("\nEnter EMP_ID of Employee to DELETE."
                        + "\nType '\u001B[31mX\u001B[0m' to RETURN/CANCEL.");
                String empIdString = sc.nextLine();

                if (empIdString.equalsIgnoreCase("X")) {
                    fx.xIsInput(this::employeeInfo);      
                } else {
                    if (fx.isNumber(empIdString)){
                        int delEmpId = Integer.parseInt(empIdString);
                        fx.cleanConsoleTwenty();
                        //will delete user access from table access first for foreign key constraint requirement
                        System.out.println("\u001B[36mDeleting Employee Access first for foreign key constraint fulfillment.\u001B[0m");
                        empQuery.deleteAccessById(delEmpId); //deletes from access table first
                        int rowsDeleted = empQuery.deleteEmployee(delEmpId);//deletes from employee table next

                        if (rowsDeleted > 0){     
                            this.delFormat();
                            empQuery.displayAllEmployee();
                        } else {
                            fx.errorMessageNoRange(this::deleteEmployee);                
                            empQuery.displayAllEmployee();                           
                        }
                    } else {
                        fx.errorMessageNoRange(this::deleteEmployee);
                    }
                }
            } catch (NumberFormatException  e) {  
                System.out.println("NumberFormatException: \u001B[31m" + e + "\u001B[0m");
                fx.errorMessageNoRange(this::deleteEmployee);
            }
        } while (true);
    }
    
    
    void addEmpAccess(){
        fx.cleanConsoleTwenty();
        genRec.empLineFormat();
        System.out.format("|%35s %31s                                                 |%n",
                "", "ADD EMPLOYEE ACCESS");

        empQuery.displayAllEmployee();//view employee table
        String level = "";
        System.out.print("Enter EMP_ID. Type '\u001B[31mX\u001B[0m' to RETURN/CANCEL: ");
        String searchIdString = sc.nextLine();
        
        try {
             if (searchIdString.equalsIgnoreCase("X")){
                 fx.xIsInput(this::employeeInfo);      
            } else {         
                if (fx.isNumber(searchIdString)){
                    int searchId = Integer.parseInt(searchIdString);
                    fx.cleanConsoleTwenty();
                    int rowsRetrieved = empQuery.selectEmployeeFieldsRet(searchId);//searches employee table first
                    if (rowsRetrieved == 1){
                        System.out.println("\nEmployee record retrieved.");
                        newEmpAccessFormat();//PetopiaHeader
                        empQuery.selectAccess(searchId);//searches access table next
                        System.out.print("Enter new username: ");
                        String newUser = sc.nextLine();
                        System.out.print("Enter new password: ");
                        String newPass = sc.nextLine();
                        do {
                            System.out.print("Enter access level \n(1) 'Admin' \n(2) 'Staff': ");
                            String access = sc.nextLine();

                            try {
                                int accessLevel = Integer.parseInt(access);
                                if (accessLevel == 1 || accessLevel == 2) {
                                    level = (accessLevel == 1) ? "Admin" : "Staff";
                                    fx.cleanConsoleTwenty();
                                    empQuery.insertAccess(newUser, newPass, level, searchId);
                                    newEmpAccessFormat();
                                    empQuery.selectAccess(searchId);
                                    fx.xToCancel(this::addEmpAccess);
                                    break; // This will exit the loop
                                } else {
                                    System.out.println("\u001B[31mInvalid input. Please enter (1) for Admin or (2) for Staff.\u001B[0m");
                                }
                            } catch (NumberFormatException e) {
                               System.out.println("NumberFormatException: \u001B[31m" + e + "\u001B[0m");
                            }
                        } while (true);                              
                    }
                }
                fx.errorMessageNoRange(this::addEmpAccess);
            }
        } catch (NumberFormatException e) {
             System.out.println("NumberFormatException: \u001B[31m" + e + "\u001B[0m");
        }
    }
    
    
    void delAccessByEmpId (){
        List <Integer> accList = empQuery.selectAccEmpId();
        this.delAccessFormat();      
        
        try {
            System.out.print("\nEnter EMP_ID to DELETE or Type '\u001B[31mX\u001B[0m' to RETURN/CANCEL: ");
            String empIdInput = sc.nextLine();
            if (empIdInput.equalsIgnoreCase("X")){
                fx.xIsInput(this::delAccess);            
            } else {
                if (fx.isNumber(empIdInput)){
                   int empId = Integer.parseInt(empIdInput); 
                   boolean found = false;
                   for (Integer acc : accList){
                       if( acc == empId){
                           found = true;
                           fx.cleanConsoleTwenty();
                           empQuery.deleteAccessById(empId);
                           this.delAccessFormat();
                           fx.xToCancel(this::delAccess);
                       }
                   }                  
                   if(!found){
                       fx.cleanConsoleTwenty();
                       System.out.println("DELETE ACCESS TABLE: \u001B[31mno EMP_ID retrieved.\u001B[0m");
                       this.delAccessByEmpId();   
                   }
                } else {
                    fx.errorMessageNoRange(this::delAccessByEmpId);
                }
            }  
        } catch (NumberFormatException e) {
             System.out.println("NumberFormatException: \u001B[31m" + e + "\u001B[0m");
        }       
    }
    
       
    void delAccessByUsernameAndPassword() {      
        this.delAccessFormat();
        System.out.println("\nYOU ARE DELETING AN EMPLOYEE ACCESS BY USERNAME AND PASSWORD.");
        System.out.print("Enter USERNAME: ");
        String username = sc.nextLine();
        System.out.print("Enter PASSWORD: ");
        String password = sc.nextLine();
        fx.cleanConsoleTwenty();
        empQuery.deleteAccessByUserPass(username, password);
        this.delAccessFormat();
        fx.xToCancel(this::delAccess);
    }
    
    
    void delAccessFormat(){
        System.out.format("\n+---------------------------------------------------------+%n");
        System.out.format("|%10s %30s                |%n", "", "DELETE EMPLOYEE ACCESS");
        empQuery.displayAllAccess();
    }
    
    
    void delAccess(){
        try {
            this.delAccessFormat(); //displays all access table
            System.out.print("\nDELETE BY: \n(\u001B[36m1\u001B[0m) EMP_ID or\n(\u001B[36m2\u001B[0m) USERNAME & PASSWORD \nType '\u001B[31mX\u001B[0m' to RETURN/CANCEL:  ");
            String input = sc.nextLine();

            if (input.equalsIgnoreCase("X")) {
                fx.xIsInput(this::employeeInfo);
            } else {               
                if (fx.isNumber(input)){
                    int delChoice = Integer.parseInt(input);
                    if (delChoice > 0 && delChoice <3) {//1 - 2
                        if (delChoice == 1) {//del by emp_id
                            fx.cleanConsoleTwenty();
                            this.delAccessByEmpId();
                        } else {//user and pass      
                             fx.cleanConsoleTwenty();
                            this.delAccessByUsernameAndPassword();    
                        }        
                    } else {
                        fx.cleanConsoleTwenty();
                        System.out.println("\u001B[31mInvalid Input. Select 1 or 2.\u001B[0m");
                        this.delAccess();
                    }
                }
                fx.errorMessageNoRange(this::delAccess);
            }
            fx.errorMessageNoRange(this::delAccess);
        } catch (NumberFormatException e) {
            System.out.println("NumberFormatException: \u001B[31m" + e + "\u001B[0m");
            sc.nextLine(); // Clear the input buffer
            this.delAccess();
        }
    }
    
    
    void viewAccess(){
        try {
            fx.xToCancel(this::employeeInfo);

        } catch (java.util.InputMismatchException e) {
            System.out.print("Invalid input. Please enter a valid integer value " + 
                             "for EMP_ID.");
            sc.nextLine(); // Clear the input buffer
            this.delAccess();
            }
    }
        
    
    void searchName(){
        System.out.print("Enter first 1 - 3 letter(s) of Employee's name:  ");
        String name = sc.nextLine();

        boolean containsLetters = false;

        for (char c : name.toCharArray()) {
            if (Character.isLetter(c)) {
                containsLetters = true;
                break;
            }
        }
        if (containsLetters) {            
            this.empSearch(name);
        } else {
            System.out.println("\nInvalid input! Enter characters.\n");            
            this.searchName();
            sc.nextLine();
        }
    }
    
    
    void empSearch(String name){
        List <Integer> likeList = empQuery.searchEmployeeLike(name); //SQL LIKE
        try {
            System.out.print("\nEnter EMP_ID of Employee to UPDATE.\nType '\u001B[31mX\u001B[0m' to RETURN/CANCEL: ");
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("X")) {
                fx.xIsInput(this::employeeInfo);// Cancel the update operation
            } else {
                if(fx.isNumber(input)){
                    int id = Integer.parseInt(input);
                   
                    boolean found = false;
                    for (Integer like : likeList) {
                        if (like == id) {//if the empId is equal to an item in the likeList then proceed 
                            found = true;
                            fx.cleanConsoleTwenty();
                            empQuery.selectEmployeeFields(id);
                            this.updateEmployee(id);
                            break;
                        }
                    }                   
                    if (!found) {
                        System.out.println("EMP_ID not in the retrieved list");
                        this.empSearch(name);
                    }                    
                } else {
                    System.out.println("Invalid input. enter an integer.");
                    this.empSearch(name);
                }    
            }
        } catch (java.util.InputMismatchException e) {
            System.out.print("\nInvalid input.\nPlease enter a valid "
                    + "integer value \nfor EMP_ID.\n\n");
            sc.nextLine(); // Clear the input buffer
            this.empSearch(name);
        }
    }
    
    
    void updateEmployee(int empId) {
        int updateField;
        String newData;
        String fieldToUpdate, field = "";
        try {
            System.out.print("\nEnter field# to update. \nType '\u001B[31mX\u001B[0m' to RETURN/CANCEL:  ");
            String updateFieldInput = sc.nextLine();

            if (updateFieldInput.equalsIgnoreCase("X")) {
                fx.xIsInput(this::employeeInfo);
            } else {
                if (fx.isNumber(updateFieldInput)){
                    try {
                        updateField = Integer.parseInt(updateFieldInput);
                        
                        if (updateField > 5){//fields up to archive is 5
                            System.out.println("Field count is only 5.");
                            this.updateEmployee(empId);
                        } else {
                            field = this.switchUpdateField(updateField);
                            boolean isUpdated = false;
                            while (!isUpdated) {
                                System.out.print("Enter new " + field + " data: "   );
                                newData = sc.nextLine();

                                switch (updateField) {
                                    case 1:
                                        System.out.println("\nUpdating FULLNAME...");
                                        fieldToUpdate = "fullname";
                                        break;
                                    case 2:
                                        System.out.println("\nUpdating EMAIL...");
                                        fieldToUpdate = "email";
                                        break;
                                    case 3:
                                        System.out.println("\nUpdating ADDRESS...");
                                        fieldToUpdate = "address";
                                        break;
                                    case 4:
                                        System.out.println("\nUpdating POSITION...");
                                        fieldToUpdate = "position";
                                        break;
                                    default://ARCHIVE
                                        try {
                                                int newDataInput = Integer.parseInt(newData);
                                                fieldToUpdate = "archived";
                                            if (newDataInput >= 0 && newDataInput < 2) { // 0 and 1 only
                                                empQuery.updateEmployee(fieldToUpdate, newData, empId);
                                                isUpdated = true; // Set the control variable to true
                                            } else {
                                                System.out.println("Invalid input. Enter only 1 or 0.");
                                            }
                                        } catch (NumberFormatException e) {
                                           System.out.println("NumberFormatException: \u001B[31m" + e + "\u001B[0m");                                                  
                                            continue; // Continue the loop to prompt for input again
                                        }
                                }
                                empQuery.updateEmployee(fieldToUpdate, newData, empId);
                                isUpdated = true; // Set the control variable to true
                            }                      
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("NumberFormatException: \u001B[31m" + e + "\u001B[0m");
                            return;
                        }
                } else {
                    System.out.println("Invalid Input. Enter an integer.");
                    this.updateEmployee(empId);
                }
            }          
        } catch (InputMismatchException e) {
            System.out.print("\nInvalid input.\nPlease enter a valid integer value for EMP_ID.\n");
            sc.nextLine(); // Clear the input buffer
            this.updateEmployee(empId);
        }
    }
  
    
    String switchUpdateField(int updateField){
        String field;
        switch (updateField) {
        case 1:
            field = "FULLNAME";
            break;
        case 2:
            field = "EMAIL";
            break;
        case 3:
            field = "ADDRESS";
            break;
        case 4:
            field = "POSITION";
            break;
        default:
            field = "ARCHIVED";
            break;
        }
        return field;
    }
}


