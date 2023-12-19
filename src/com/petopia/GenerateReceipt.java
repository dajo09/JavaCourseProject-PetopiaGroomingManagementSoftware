
package com.petopia;

import com.petopia.api.GenerateReceiptQuery;
import com.petopiables.PetopiaHeader;
import com.petopiables.Functions;
import java.util.List;
import java.util.Scanner;


public class GenerateReceipt extends PetopiaHeader{
    public Scanner sc = new Scanner(System.in);
    public static GenerateReceiptQuery genQuery = new GenerateReceiptQuery();
    public Functions fx = new Functions();
    
    public void generateReceipt(){
         //instantiations
        Services services = new Services();
        LogIn logIn = new LogIn();
           
        System.out.println(logIn.qLevel + " - " + logIn.qUsername);
        petopiaHeader();
        
        System.out.format("|           GENERATE RECEIPTS           |%n");
        System.out.format("+---------------------------------------+%n");
        System.out.format("|                                       |%n");   
        System.out.format("|                                       |%n");   
        System.out.format("|       (\u001B[36m1\u001B[0m) VIEW RECEIPTS TABLE         |%n");
        System.out.format("|       (\u001B[36m2\u001B[0m) PRINT RECEIPT               |%n");
        System.out.format("|       (\u001B[36m3\u001B[0m) VOID RECEIPT                |%n");
        System.out.format("|       (\u001B[36m4\u001B[0m) CANCEL/RETURN               |%n");
        padThreeBars();
        System.out.format("|                                       |%n");   
        System.out.format("+---------------------------------------+%n");
        
        try {
            System.out.print("Enter a field: ");
            String choiceInput = sc.nextLine();
            if (fx.isNumber(choiceInput)){
                int choice = Integer.parseInt(choiceInput);
                if (choice > 0 && choice < 5) {
                    if (choice == 1){//VIEW RECEIPTS TABLE whether void or not
                        fx.cleanConsoleTwenty();
                        this.viewReceipt();
                    } else if (choice == 2) {//print receipt 
                        fx.cleanConsoleTwenty();
                        this.printReceipt();
                        fx.xToCancel(this::generateReceipt);
                    } else if (choice == 3) {//void receipts
                        fx.cleanConsoleTwenty();
                        this.voidReceipt();
                        fx.xToCancel(this::generateReceipt);
                    } else {
                        fx.cleanConsoleTwenty();
                        services.services(logIn.qUsername, logIn.qLevel);
                    }
                } else {
                     fx.errorMessage("1 - 4", this::generateReceipt);
                }
            } else {
                 fx.errorMessage("1 - 4", this::generateReceipt);
            }
        } catch (NumberFormatException e) {
            System.out.println("NumberFormatException: " + e);
            fx.errorMessage("1 - 4", this::generateReceipt);
        }
    }
                
    
    public void viewReceipt(){
        GenerateReceipt genRec = new GenerateReceipt();
        
        genRec.lineFormat();
        System.out.format("|%35s %32s                                                      |%n",
                "", "VIEW RECEIPTS");
        List <Integer> viewList = genQuery.selectReceiptsId();
        genQuery.selectAllReceipts(); // void and non-void
        
        try {
            System.out.print("Enter Invoice # to VIEW. Type '\u001B[31mX\u001B[0m' to RETURN/CANCEL:  ");
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("X")) {
                fx.xIsInput(this::generateReceipt);      
            } else {
                if (fx.isNumber(input)){
                    int invNo = Integer.parseInt(input);
                    boolean found = false;
                    System.out.println(viewList);
                    for (Integer view : viewList) {
                        if (view == invNo) {
                            found = true;
                            fx.cleanConsoleTwenty();
                            System.out.format("+--------------------------------------------------------+%n");
                            System.out.format("|                      VIEW RECEIPT                      |%n");
                            System.out.format("|                     RECEIPT TABLE                      |%n");
                            System.out.format("+--------------------------------------------------------+%n\n");
                            genQuery.selectReceiptAllFields(invNo);
                            fx.xToCancel(this::viewReceipt);
                        }               
                    }
               
                    if (!found) {
                        fx.cleanConsoleTwenty();
                        System.out.println("VIEW RECEIPTS TABLE: \u001B[31mno Receipt# retrieved.\u001B[0m");
                        this.viewReceipt();            
                    }
                } else {
                     fx.errorMessageNoRange(this::viewReceipt);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("NumberFormatException: \u001B[31m" + e + "\u001B[0m");
            fx.errorMessageNoRange(this::viewReceipt);
        }
    }
    
    
    void printReceipt(){
        GenerateReceipt genRec = new GenerateReceipt();
        
        genRec.lineFormat();
        System.out.format("|%35s %32s                                                      |%n",
                                 "", "PRINT RECEIPTS");  
        genQuery.selectAllReceiptsNotVoid(); //only prints non-void receipts
         List <Integer> invList = genQuery.selectReceiptsIdNotVoid();
        
        try {
            System.out.print("\nEnter a receipt or invoice# to PRINT.\nType '\u001B[31mX\u001B[0m' to RETURN/CANCEL: ");
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("X")) {
                fx.xIsInput(this::generateReceipt);   
            } else {
                if (fx.isNumber(input)) {
                    int invNo = Integer.parseInt(input);
                    boolean found = false;
                    for (Integer inv : invList) {
                        if (inv == invNo) {
                            found = true;
                            fx.cleanConsoleTwenty();
                            System.out.println("\n\nGenerating Official Receipt......\n\n");
                            System.out.format("+--------------------------------------------------------+%n");
                            System.out.format("|                   OFFICIAL RECEIPT                     |%n");
                            System.out.format("|                PETOPIA GROOMING SERVICE                |%n");
                            System.out.format("+--------------------------------------------------------+%n\n");
                            genQuery.selectReceipt(inv);
                            System.out.format("\n----------------------------------------------------------%n\n");
                            fx.xToCancel(this::printReceipt);
                        }
                    }
                    
                   if (!found) {
                       fx.cleanConsoleTwenty();
                       System.out.println("PRINT RECEIPTS TABLE: \u001B[31mno Receipt# retrieved.\u001B[0m");
                       this.printReceipt();
                   }
                   
                } else {
                    fx.errorMessageNoRange(this::printReceipt);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("NumberFormatException: \u001B[31m" + e + "\u001B[0m");
            fx.errorMessageNoRange(this::printReceipt);
        }     
    }    
    
    
    
    void voidReceipt() {
        GenerateReceipt genRec = new GenerateReceipt();
        
        genRec.lineFormat();
        System.out.format("|%35s %32s                                                      |%n",
                                 "", "VOID RECEIPTS"); 
        genQuery.selectAllReceiptsNotVoid();
        List <Integer> invList = genQuery.selectReceiptsIdNotVoid();
        
        try {
            System.out.print("\nEnter a receipt or invoice# to VOID.\nType '\u001B[31mX\u001B[0m' to RETURN/CANCEL: ");
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("X")){
                fx.xIsInput(this::generateReceipt);   
            } else {
                if (fx.isNumber(input)){
                    int invNo = Integer.parseInt(input);
                    boolean found = false;
                    for (Integer inv : invList){
                        if(invNo == inv) {
                            found = true;
                            genRec.lineFormat();
                            System.out.format("|%35s %32s                                                      |%n",
                                                     "", "VOID RECEIPTS"); 
                            genQuery.voidReceipt(inv);
                            fx.xToCancel(this::voidReceipt);
                        }
                    }
                    
                    if (!found){
                        fx.cleanConsoleTwenty();
                        System.out.println("VOID RECEIPTS TABLE: \u001B[31mno Receipt# retrieved.\u001B[0m");
                        this.voidReceipt();
                    }
                } else {
                    fx.errorMessageNoRange(this::voidReceipt);
                }
            }
        }catch (NumberFormatException e) {
            System.out.println("NumberFormatException: \u001B[31m" + e + "\u001B[0m");
            fx.errorMessageNoRange(this::printReceipt);
        }
    }    
}

            
         

        
 