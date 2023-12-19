
package com.petopiables;


public class Employee {
    private int empId;
    private String fullname;
    private String email;
    private String address;
    private String position;
    private int archived;
    
    public Employee(int empId, String fullname, String email, String address, String position, int archived){
        this.empId = empId;
        this.fullname = fullname;
        this.email = email;
        this.address = address;
        this.position = position;
        this.archived = archived;
    }

    
    public int getArchived() {
        return archived;
    }

    
    public void setArchived(int archived) {
        this.archived = archived;
    }

    
    public int getEmpId() {
        return empId;
    }

    
    public void setEmpId(int empId) {
        this.empId = empId;
    }

    
    public String getFullname() {
        return fullname;
    }

    
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    
    public String getEmail() {
        return email;
    }

    
    public void setEmail(String email) {
        this.email = email;
    }

    
    public String getAddress() {
        return address;
    }

    
    public void setAddress(String address) {
        this.address = address;
    }

    
    public String getPosition() {
        return position;
    }

    
    public void setPosition(String position) {
        this.position = position;
    } 
}
