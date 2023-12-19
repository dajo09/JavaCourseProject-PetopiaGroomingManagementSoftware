
package com.petopiables;


public class Pet {
    
    private int id;
    
    private String name;
    
    private int type;
    
    private String description;
    
    private int age;
    
    private String breed;
    
    private String ownerName;

    private int customerId;
    
    private int archived;
    
 
    public Pet() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {

        if(id < 0) {
            throw new IllegalArgumentException("pet id cannot be negative");
        }

        this.id = id;
      
    }

    
    public String getName() {
 
        return name;
    }

    
    public void setName(String name) {
        
        try {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("pet name cannot be null or empty");
             }
            this.name = name;
       
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
  
    }

    
    public int getType() {
        return type;
    }

    
    public void setType(int type) {
        this.type = type;
                
    }

    
    public int getAge() {       
        return age;
    }

    
    public void setAge(int age) {
        try {
            if(age < 0) {
                throw new IllegalArgumentException("age can not be negative value");
            }
          this.age = age;

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    
    public String getBreed() {
        return breed;
    }

    
    public void setBreed(String breed) {
        
        try {
            if (breed == null || breed.trim().isEmpty()) {
                throw new IllegalArgumentException("Pet breed should not be empty");
            }
             this.breed = breed;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    
    public int getCustomerId() {
        return customerId;
    }

    
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    
    public int getArchived() {
        return archived;
    }

    
    public void setArchived(int archived) {
        
        try {
           if (archived != 0 && archived != 1) {
                throw new IllegalArgumentException("Invalid archived value. The value must be either 0 or 1.");
           }
            this.archived = archived;
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid archived value: " + e.getMessage());
        }
    }
    
    
    public String getDescription() {      
        return description;
    }

    
    public void setDescription(String description) {
        
        try {
            if(description.length() > 25){
                throw new IllegalArgumentException("Pet description should just contain of 25 characters");
            }
              this.description = description;
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
      
    }

    
    public String getOwnerName() {    
        return ownerName;
    }

    
    public void setOwnerName(String ownerName) {
        
        try {
            if(ownerName == null || ownerName.trim().isEmpty()){
                throw new IllegalArgumentException("Owner name should not be empty");
            }
             this.ownerName = ownerName;

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }    
    } 
}
