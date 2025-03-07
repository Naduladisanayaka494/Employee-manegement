package com.employeemanegement.employee_manegement.services.auth;

import com.employeemanegement.employee_manegement.dto.SignUpRequest;
import com.employeemanegement.employee_manegement.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements  AuthService{


    @Override
    public UserDto createEmployee(SignUpRequest signuprequest) {
        return null;
    }

    @Override
    public UserDto createdAdmin(SignUpRequest signuprequest) {
        return null;
    }

    @Override
    public boolean hasAdminwithemail(String email) {
        return false;
    }
}
