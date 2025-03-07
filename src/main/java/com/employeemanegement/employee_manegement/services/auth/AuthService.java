package com.employeemanegement.employee_manegement.services.auth;


import com.employeemanegement.employee_manegement.dto.SignUpRequest;
import com.employeemanegement.employee_manegement.dto.UserDto;

public interface AuthService {
    UserDto createEmployee(SignUpRequest signuprequest);
    UserDto createdAdmin(SignUpRequest signuprequest);
    boolean hasAdminwithemail(String email);
}
