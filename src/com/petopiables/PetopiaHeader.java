
package com.petopiables;

public class PetopiaHeader {
    
    public void petopiaHeader(){
        System.out.format("+---------------------------------------+%n");
        System.out.format("|       \u001B[31mPetopia Management System\u001B[0m       |%n"); //Blue
        System.out.format("+---------------------------------------+%n");
    }
    
    public void petopiaHeaderExtended(){
        System.out.format("+----------------------------------------------------------------------------------------------------------------+%n");
        System.out.format("|%35s %37s                                       |%n", "", "\u001B[31mPetopia Management System\u001B[0m");
        System.out.format("+----------------------------------------------------------------------------------------------------------------+%n");
    }
    //THIS PART IS EMPLOYEE, EMPLOYEE QUERY AND SERVICE CLASSES ONLY
    //EMPLOYEE CLASS
    public void updateFormat(){
        System.out.format("\n+---------------------------------------+%n");
        System.out.format("|      UPDATE EMPLOYEE INFORMATION      |%n");
    }
    //EMPLOYEE CLASS        
    public void accessFormat(){
        System.out.format("|%10s %23s                       |%n", "", "ACCESS TABLE");
        System.out.format("+---------------------------------------------------------+%n\n");
    }
    //EMPLOYEE CLASS
    public void empFormat(){
        System.out.format("|             EMPLOYEE TABLE            |%n");
        System.out.format("+---------------------------------------+%n");
    }
    //EMPLOYEE CLASS
    public void empFormatExtended(){
        System.out.format("|%35s %28s                                                    |%n", "", "EMPLOYEE TABLE");
        System.out.format("+--------------------------------------------------------------------------------------------------------------------+%n\n");
    }
    //EMPLOYEE CLASS
    public void newEmpAccessFormat(){
        System.out.format("\n+---------------------------------------------------------+%n");
        System.out.format("|%10s %30s                |%n", "", "ADD NEW EMPLOYEE ACCESS");
    }
    //EMPLOYEE CLASS
    public void empLineFormat(){
        System.out.format("+---------------------------------------------------------"
                + "-----------------------------------------------------------+%n");
    }
    //SERVICE CLASS
    public void padThreeBars(){
        for (int i = 0; i < 3; i++) {
             System.out.format("|                                       |%n");   
        }
    }
    //  GENERATE RECEIPT CLASS
    public void genRepFormat(){
        System.out.format("|%35s %32s                                                      |%n", "", "RECEIPT TABLE");
        System.out.format("+--------------------------------------------------------------------------------------------------------------------------+%n");
    }
    //GENERATE RECEIPT CLASS
    public void lineFormat(){
        System.out.format("+--------------------------------------"
                        + "------------------------------------------------------------------------------------+%n");
    }
    
    
 
    

//PLEASE ADD YOUR CLASSES BELOW HERE SO WE DON'T HAVE A PROBLEM MERGING & UPDATING FILES     
      public void displayServiceDetailsHeader(){
        // Print header
        System.out.format("+----------------------------------------------------------+%n");
        System.out.format("|                 GROOMING SERVICE DETAILS                 |%n");
        System.out.format("+----------------------------------------------------------+%n");
    }
    
    public void availableServiceHeader(){
        System.out.format("|      AVAILABLE GROOMING SERVICE       |%n");
        System.out.format("+---------------------------------------+%n");
    }
    
    public void displayAllServiceColumnsHeader(){
        System.out.println(String.format("| %s  %-25s %-10s  %-14s |", "ID", "DESCRIPTION", "PRICE", "AVAILABILITY"));
        System.out.format("+----------------------------------------------------------+%n");
    }
    public void displayAvailableServiceHeader(){
        System.out.println(String.format("| %s  %-25s %-7s |", "ID", "DESCRIPTION", "PRICE"));
        System.out.format("+---------------------------------------+%n");
    }
    
    public void cancelOrReturnHeaderLong() {
        System.out.println("| (6) CANCEL/RETURN                                        |");
        System.out.println("+----------------------------------------------------------+");
    }
    
    public void displayPaymentMethodsHeader() {
        System.out.format("+---------------------------------------+%n");
        System.out.format("|        SELECT PAYMENT METHOD          |%n");
        System.out.format("+---------------------------------------+%n");
    }
    
    
 
    //  ADD OTHER FORMATS HERE
    
}
