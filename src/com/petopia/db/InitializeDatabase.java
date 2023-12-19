
package com.petopia.db;

public class InitializeDatabase extends DatabaseConnections{
    
    public void initializeDatabase(){
        System.out.println("Starting database......");
        connectToDatabase();
        System.out.println("\nDatabase connected successfully!\n\n");
    }
}
