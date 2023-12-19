
package com.petopiables;

import java.util.Scanner;

/*
FUNCTION THAT'S USED AMONG SEVERAL CLASSES
*/

public class Functions {
    public boolean isNumber(String input) {//takes in an input and checks if it's an integer or not
        try {
            Integer.valueOf(input);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("NumberFormatException: \u001B[31m" + e + "\u001B[0m");
            return false;
        }
    }
    
    
    public void errorMessage(String range, Runnable windowAction){
        this.cleanConsoleTwenty();
        System.out.println("\u001B[31mInvalid Input! Enter choice (" + range + ").\u001B[0m");
        windowAction.run();
    }
    
    
    public void errorMessageNoRange(Runnable windowAction){
        this.cleanConsoleTwenty();
        System.out.println("\u001B[31mInvalid input. Enter an integer.\u001B[0m");
        windowAction.run();
    }
    
    
    public void cleanConsoleTwenty(){ // loops a new line 20 times to clear the console of previous lines
        for (int i = 0; i < 20; i++) {
        System.out.print("\n");
        }
    }
    
    
    public void xToCancel(Runnable windowAction){
        Scanner sc = new Scanner(System.in);
        
        System.out.print("\nType '\u001B[31mX\u001B[0m' to RETURN/CANCEL."); // 'X' in red
        String input = sc.nextLine();
            if (input.equalsIgnoreCase("X")) {
                this.cleanConsoleTwenty();
                windowAction.run();
            } else {
                System.out.println("\u001B[31mInvalid input. Enter 'X'  to RETURN/CANCEL.\u001B[0m");
                this.xToCancel(windowAction);
            }
    }
    
    
    public void xIsInput(Runnable windowAction){
        this.cleanConsoleTwenty();
        windowAction.run();
    }
    
    
    
    
    
    //ADD YOUR FUNCTIONS BELOW THIS LINE
    
}
