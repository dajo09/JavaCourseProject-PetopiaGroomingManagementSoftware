
package com.petopiables;


public class EmployeeAccess {
    private String username;
    private String password;
    private String level;
    private int emp_id;
   
    public EmployeeAccess(String username, String password, String level, int emp_id) {
        this.username = username;
        this.password = password;
        this.level = level;
        this.emp_id = emp_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccessLevel() {
        return level;
    }

    public void setAccessLevel(String level) {
        this.level = level;
    }
    
    public int getEmpId() {
        return emp_id;
    }
}
