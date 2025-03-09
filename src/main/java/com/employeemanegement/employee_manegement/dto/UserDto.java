package com.employeemanegement.employee_manegement.dto;



import com.employeemanegement.employee_manegement.Enum.UserRole;
import lombok.Data;

@Data
public class UserDto {



    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String name;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    private String email;

    private UserRole userRole;

}
