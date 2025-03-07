package com.employeemanegement.employee_manegement.dto;


import com.employeemanegement.employee_manegement.Enum.UserRole;
import lombok.Data;


@Data
public class AuthenticationResponse {
    private String jwt;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    private UserRole userRole;
    private Long userId;
}

