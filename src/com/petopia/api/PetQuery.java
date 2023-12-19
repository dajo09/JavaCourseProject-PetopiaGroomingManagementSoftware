
package com.petopia.api;

import com.petopia.db.DatabaseConnections;
import com.petopiables.Customer;
import com.petopiables.Pet;
import java.sql.SQLException;

public class PetQuery extends DatabaseConnections {
   Pet pet = new Pet();
   Customer customer = new Customer();
    
   private static final String PET_QUERY_FORMAT = "%-10s %-20s %-20s %-10s %-20s %-20s %-10s %n";
   
   public void addNewPet(String name, int type, int age, String breed, int customerId, int archived) {
       
       String query = "INSERT INTO pet (name, type_id, age, breed, cust_id, archived) VALUES (?, ?, ?, ?, ?,?)";

        try {
            connectToDatabase();

            // prepareStatement
            pStatement = connection.prepareStatement(query);
            pStatement.setString(1, name);
            pStatement.setInt(2, type);
            pStatement.setInt(3, age);
            pStatement.setString(4, breed);
            pStatement.setInt(5, customerId);
            pStatement.setInt(6, archived);

            // insert the data
            int check = pStatement.executeUpdate();

            if (check > 0) {
                System.out.println("Pet " + name + " successfully added!");
            } else {
                System.out.println("Query failed.");
            }
            
            closeAllConnections();
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
    }


   public void selectPetFields(int petId) {
        String query = "SELECT * FROM pet WHERE pet_id = ?";
        try {
            connectToDatabase();

            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, petId);
            result = pStatement.executeQuery();

            while (result.next()) {
                int id = result.getInt("pet_id");
                String name = result.getString("name");
                int typeId = result.getInt("type_id");
                int age = result.getInt("age");
                String breed = result.getString("breed");
                int customerId = result.getInt("cust_id");
                int archived = result.getInt("archived");
                System.out.format("+---------------------------------------+%n");
                System.out.format("|            PET INFORMATION            |%n");
                System.out.format("+---------------------------------------+%n");
                System.out.println("\n(1) NAME: \t" + name + "\n(2) TYPE ID: \t" + typeId + "\n(3) AGE: \t"
                        + age + "\n(4) BREED: \t" + breed + "\n(5) CUSTOMER ID: \t" + customerId + "\n(6) ARCHIVED: \t" + archived + "\n");
                System.out.format("+---------------------------------------+%n\n");
            }
            closeAllConnections();
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
    }
   
   
    public void updatePet(String field, Object newData, int petId) {
  
        String query = "UPDATE pet SET " + field + " = ? WHERE pet_id = ?";
        System.out.println(query);
        try {
            connectToDatabase();
            pStatement = connection.prepareStatement(query);

            if (newData instanceof String) {
                pStatement.setString(1, (String) newData);
            } else if (newData instanceof Integer) {
                pStatement.setInt(1, (Integer) newData);
            }

            pStatement.setInt(2, petId);

            int rowsAffected = pStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) updated successfully.");
            this.selectPetFields(petId);
          
            closeAllConnections();
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
}
    
    public void archivedPet(int id){
        
         String query = "UPDATE pet SET pet.archived = 1 where pet_id = ?";
         
         try {  
          
            connectToDatabase();
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, id);
            
            int rowsAffected = pStatement.executeUpdate();          
            System.out.println(rowsAffected + " row(s) is successfully archived");
            closeAllConnections();
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
    }
    
    
    public void displaytAllPets(){
      // PetInfo petInfo = new PetInfo();
        String query = "Select * from pet "
                + "inner join type on pet.type_id = type.type_id "
                + "inner join customer on pet.cust_id = customer.cust_id "
                + "WHERE  pet.archived = 0";
        
        try {
            connectToDatabase();
            pStatement = connection.prepareStatement(query);
            result = pStatement.executeQuery(query);
            System.out.printf(PET_QUERY_FORMAT,
                    "PET ID", "NAME","DESCRIPTION", "AGE","BREED","OWNER NAME", "ARCHIVED");
          
            while(result.next()){
                int petId = result.getInt("pet_id");
                String name = result.getString("name");
                String desc = result.getString("desc");
                int age = result.getInt("age");
                String breed = result.getString("breed");
                String ownerName = result.getString("fullname");
                int archived = result.getInt("archived");
                               
                pet.setId(petId);              
                pet.setName(name);
                pet.setDescription(desc);
                pet.setAge(age);
                pet.setBreed(breed);
                pet.setOwnerName(ownerName);
                pet.setArchived(archived);
                               
                System.out.format(" %-7d  %-20s  %-20s  %-7d  %-25s  %-15s %-5d %n",  
                            pet.getId(),pet.getName(), pet.getDescription(), 
                            pet.getAge(), pet.getBreed(), pet.getOwnerName(),  pet.getArchived());

            }
           
            closeAllConnections();
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
    }   
   
    
    public void retrivePet(int id){
          String query = "update pet set pet.archived = 0 where pet_id = ?";
          String selectName = "SELECT name from pet where pet_id = ?";
        try {
            connectToDatabase();
            
            //Update the pet's archived status
            pStatement = connection.prepareStatement(query);     
            pStatement.setInt(1, id);
            pStatement.executeUpdate();
            
            //Retrive the name of pet
            pStatement = connection.prepareStatement(selectName);          
            pStatement.setInt(1, id);
            result = pStatement.executeQuery();
          
            if (result.next()) {
             String name = result.getString("name");
             System.out.println("Pet " + name + " with ID of " + id + " is successfully unarchived!");
            } else {
                System.out.println("Pet with ID " + id + " not found.");
            }
                  
            closeAllConnections();
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
    }
    
    
    public void displayDeletedPets(){
        //todo - show options in type
        //PetInfo petInfo = new PetInfo();
        String query = "Select * from pet "
                + "inner join type on pet.type_id = type.type_id "
                + "inner join customer on pet.cust_id = customer.cust_id "
                + "where pet.archived = 1";
        
        try {
            connectToDatabase();
            pStatement = connection.prepareStatement(query);
            result = pStatement.executeQuery(query);
            System.out.printf(PET_QUERY_FORMAT,
                    "PET ID", "NAME","DESCRIPTION", "AGE","BREED","OWNER NAME", "ARCHIVED");

            while(result.next()){
                int petId = result.getInt("pet_id");
                String name = result.getString("name");
                String desc = result.getString("desc");
                int age = result.getInt("age");
                String breed = result.getString("breed");
                String ownerName = result.getString("fullname");
                int archived = result.getInt("archived");
                
                pet.setId(petId);
                pet.setName(name);
                pet.setDescription(desc);
                pet.setAge(age);
                pet.setBreed(breed);
                pet.setOwnerName(ownerName);
                pet.setArchived(archived);        
                 
                System.out.format(" %-7d  %-20s  %-20s  %-7d  %-25s  %-15s %-5d %n",  
                            pet.getId(),pet.getName(), pet.getDescription(), 
                            pet.getAge(), pet.getBreed(), pet.getOwnerName(),  pet.getArchived());
                          
                System.out.println();
            }         
            closeAllConnections();
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }   
    }
    
    
    //it only accepts either id or name
    public void searchPetByIdOrByName(int searchById, String searchByName){
     
        String query = "SELECT * FROM pet "
                + "INNER JOIN type ON pet.type_id = type.type_id "
                + "INNER JOIN customer ON pet.cust_id = customer.cust_id "
                + "WHERE pet.pet_id = ? OR pet.name = ? ";
                // + "AND pet.archived = 0";  
        try {
            connectToDatabase();
            pStatement = connection.prepareStatement(query);    
            pStatement.setInt(1, searchById);      
            pStatement.setString(2, searchByName);
         
            result = pStatement.executeQuery();
           
             System.out.printf(PET_QUERY_FORMAT,
                     "PET ID", "NAME", "DESCRIPTION", "AGE", "BREED", "OWNER NAME", "ARCHIVED");
         
            while(result.next()){
                int petId = result.getInt("pet_id");
                String name = result.getString("name");
                String desc = result.getString("desc");
                int age = result.getInt("age");
                String breed = result.getString("breed");
                String ownerName = result.getString("fullname");
                int archived = result.getInt("archived");
                
                pet.setId(petId);
                pet.setName(name);
                pet.setDescription(desc);
                pet.setAge(age);
                pet.setBreed(breed);
                pet.setOwnerName(ownerName);
                pet.setArchived(archived);
                
                if(pet.getArchived() == 1){           
                    System.out.println("The pet is in archived");            
                } else if (pet.getArchived() == 0) {              
                    System.out.format(" %-7d  %-20s  %-20s  %-7d  %-25s  %-15s %-5d %n",  
                            pet.getId(),pet.getName(), pet.getDescription(), 
                            pet.getAge(), pet.getBreed(), pet.getOwnerName(),  pet.getArchived());
           
                    System.out.println();
               
                } else {
                    System.out.println("Pet not found in database");
                }
            }
           
            closeAllConnections();
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
    }
    

    public void displayPetType(){
            String query = "SELECT * FROM type";
            
            try {
            connectToDatabase();
            pStatement = connection.prepareStatement(query);
            result = pStatement.executeQuery(query);
             
            System.out.format("+---------------------------------------+%n");
            System.out.format("|            PET TYPES                  |%n");
            System.out.format("+---------------------------------------+%n");
           
            while(result.next()){
                int typeId = result.getInt("type_id");
                String desc = result.getString("desc"); 
              
                pet.setType(typeId);          
                pet.setDescription(desc);
           
                System.out.println("ID: " + pet.getType() + "\t" + "DESCRIPTION: " + pet.getDescription());
                System.out.format("+---------------------------------------+%n\n");
            }                          
            System.out.println();
           
            closeAllConnections();
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
   }

    
    public void displayOwnerName() {
        String query = "SELECT * FROM customer";
            
            try {
            connectToDatabase();
            pStatement = connection.prepareStatement(query);
            result = pStatement.executeQuery(query);
             
            System.out.format("+---------------------------------------+%n");
            System.out.format("|            CUSTOMER ID                |%n");
            System.out.format("+---------------------------------------+%n");
           
            while(result.next()){
                int custId = result.getInt("cust_id");
                String name = result.getString("fullname"); 
              
                customer.setId(custId);
                customer.setFullName(name);
                      
                System.out.println("CUSTOMER ID NO: " + customer.getId() + "\t" + "FULL NAME: " + customer.getFullName());
                System.out.format("+---------------------------------------+%n\n");
            }                      
            System.out.println();
           
            closeAllConnections();
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
    }
    
    
    public boolean petExist(int petId, String petName) {
        String query = "SELECT * FROM pet WHERE pet_id = ? OR name = ?";

        try {
           connectToDatabase();
           pStatement = connection.prepareStatement(query);
           pStatement.setInt(1, petId);
           pStatement.setString(2, petName);
           result = pStatement.executeQuery();

           boolean petExists = (result != null && result.next());
           closeAllConnections();
           return petExists;       
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
        return false;  
    }

    
    public int displayPetByDogOwners(int cust_id){
        int petId = 0;
        String query = "Select pet.pet_id, pet.name, pet.cust_id, pet.archived from pet "
                + "inner join customer on pet.cust_id = customer.cust_id "
                + "where pet.archived = 0 and customer.cust_id = ?";
        
        try {
            connectToDatabase();
            pStatement = connection.prepareStatement(query);
            // set parameter values
            pStatement.setInt(1, cust_id);
            result = pStatement.executeQuery();
            System.out.format("+----------------------------------------------------------+%n");
            System.out.println(String.format("|%7s  %-20s %10s  %14s |","PET_ID", "PET NAME", "CUSTOMER ID", "ARCHIVED"));
            System.out.format("+----------------------------------------------------------+%n");
            while(result.next()){
                petId = result.getInt("pet_id");
                String petName = result.getString("name");
                int custid = result.getInt("cust_id");
                int archived = result.getInt("archived");

                // connection to view 
                System.out.println(String.format("| %-7s %-10s %12d  %16d        |", petId, petName, custid, archived));
                System.out.format("+----------------------------------------------------------+%n");    
            }
            closeAllConnections();  
        } catch (SQLException e) {
            System.out.println("Error executing query: \u001B[31m" + e.getMessage() + "\u001B[0m");
        }
        return petId;
    }   
}