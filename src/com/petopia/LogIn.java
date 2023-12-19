
package com.petopia;

import com.petopia.api.LogInQuery;
import com.petopiables.PetopiaHeader;
import com.petopiables.EmployeeAccess;
import com.petopiables.Functions;
import java.util.Scanner;


public class LogIn extends PetopiaHeader {
    //instantiations
    Scanner sc = new Scanner(System.in);
    LogInQuery query = new LogInQuery();
   
    public static String qUsername, qLevel;//will be called between classes; used for return/cancel
    public static int qEmpId;
    public static int currentUserId;
    
    public void LogIn(){
        Functions fx = new Functions();
        Services services = new Services();
        String qPassword = "";
        
        petopiaHeader(); 

        System.out.print("\n           USERNAME: ");
        String username = sc.nextLine();    
        System.out.print("           PASSWORD: ");
        String password = sc.nextLine();
        
        
        for (EmployeeAccess employeeAccess : query.retrieveAccess(username, password) ) {
                qUsername = employeeAccess.getUsername(); // encapsulation: getter
                qPassword = employeeAccess.getPassword();
                qLevel = employeeAccess.getAccessLevel(); // encapsulation: getter
                qEmpId = employeeAccess.getEmpId();
            }
            System.out.println("");

        if (username.equals(qUsername) && password.equals(qPassword) ){
            //userName and password matches access then get accesslevel whether Admin or Staff
            fx.cleanConsoleTwenty();
            services.services(qUsername, qLevel);
            
        }else {
            fx.cleanConsoleTwenty();
            System.out.println("\u001B[31m\n\n\nInvalid LogIn Credentials! Try again.\u001B[0m");
            LogIn();
        } 
        currentUserId = qEmpId;
    } 
    
    
    public int getCurrentUserId(){
        return currentUserId = qEmpId;
    }
}
