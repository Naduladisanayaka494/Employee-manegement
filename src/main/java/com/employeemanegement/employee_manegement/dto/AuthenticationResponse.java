

// AuthenticationResponse.java
package com.employeemanegement.employee_manegement.dto;

        import com.employeemanegement.employee_manegement.Enum.UserRole;
        import lombok.Data;

@Data
public class AuthenticationResponse {
    private String jwt;
    private UserRole userRole;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String userId; // Changed to String for MongoDB
}
