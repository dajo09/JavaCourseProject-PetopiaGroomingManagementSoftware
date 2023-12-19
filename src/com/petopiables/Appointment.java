
package com.petopiables;

public class Appointment {
    private int id;
    private int servId;
    private int custId;
    private String servDesc;
    private String customerName;
    private String appointmentDate;
    private double totalCost;
    private int status;
    private String date;

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

    
    public int getServId() {
        return servId;
    }
    
    //to ensure that the provided id value is positive.
    public void setServId(int servId) {
        if (servId > 0) {
            this.servId = servId;
        } else {
            throw new IllegalArgumentException("Service ID must be a positive integer.");
        }
    }

    
    public int getCustId() {
        return custId;
    }
    
   //to ensure that the provided id value is positive.
    public void setCustId(int custId) {
        if (custId > 0) {
            this.custId = custId;
        } else {
            throw new IllegalArgumentException("Customer ID must be a positive integer.");
        }
    }

    
    public String getAppointmentDate() {
        return appointmentDate;
    }
   
    //checks if the provided appointmentDate value is not null and not an empty string
    public void setAppointmentDate(String appointmentDate) {
        if (appointmentDate != null && !appointmentDate.isEmpty()) {
            this.appointmentDate = appointmentDate;
        } else {
            throw new IllegalArgumentException("Appointment date cannot be null or empty.");
        }
    }

    
    public double getTotalCost() {
        return totalCost;
    }

    
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    
    public int getStatus() {
        return status;
    }

    
    public void setStatus(int status) {
        this.status = status;
    }

    
    public String getServDesc() {
        return servDesc;
    }

    //checks if the provided desc value is not null and not an empty string
    public void setServDesc(String servDesc) {
        if (servDesc != null && !servDesc.isEmpty()) {
            this.servDesc = servDesc;
        } else {
            throw new IllegalArgumentException("Service description cannot be null or empty.");
        }
    }

    
    public String getCustomerName() {
        return customerName;
    }

    //checks if the provided customer name value is not null and not an empty string
    public void setCustomerName(String customerName) {
        if (customerName != null && !customerName.isEmpty()) {
            this.customerName = customerName;
        } else {
            throw new IllegalArgumentException("Customer name cannot be null or empty.");
        }
    }

    
    public String getDate() {
        return date;
    }
    
    //checks if the provided date value is not null and not an empty string
    public void setDate(String date) {
        if (date != null && !date.isEmpty()) {
            this.date = date;
        } else {
            throw new IllegalArgumentException("Date cannot be null or empty.");
        }
    }
}