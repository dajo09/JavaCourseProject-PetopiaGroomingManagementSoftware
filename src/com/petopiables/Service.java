
package com.petopiables;


public class Service {
      
    private int id;
    
    private String desc;
    
    private double price;
    
    private int available;

    public int getId() {
        return id;
    }

    //to ensure that the provided id value is positive.
    public void setId(int id) {
        if (id > 0) {
            this.id = id;
        } else {
            throw new IllegalArgumentException("ID must be a positive integer.");
        }
    }
    public String getDesc() {
        return desc;
    }
    
    //checks if the provided desc value is not null and not an empty string
    public void setDesc(String desc) {
       if (desc != null && !desc.isEmpty()) {
            this.desc = desc;
        } else {
            throw new IllegalArgumentException("Service description cannot be null or empty.");
        }
    }
 

    public double getPrice() {
        return price;
    }

    
    public void setPrice(double price) {
        this.price = price;
    }

    
    public int getAvailable() {
        return available;
    }

    
    public void setAvailable(int available) {
        this.available = available;
    }  
}
