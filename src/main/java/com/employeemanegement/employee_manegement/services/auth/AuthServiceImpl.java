package com.employeemanegement.employee_manegement.services.auth;

import com.employeemanegement.employee_manegement.dto.SignUpRequest;
import com.employeemanegement.employee_manegement.dto.UserDto;
import com.employeemanegement.employee_manegement.entity.User;
import com.employeemanegement.employee_manegement.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.employeemanegement.employee_manegement.Enum.UserRole;
@Service
public class AuthServiceImpl implements  AuthService{
    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createEmployee(SignUpRequest signuprequest) {
        User user = new User();
        user.setName(signuprequest.getName());
        user.setEmail(signuprequest.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(signuprequest.getPassword()));
        user.setUserRole(UserRole.Employee);
        User createduser  =userRepository.save(user);
        UserDto userdto = new UserDto();
        userdto.setId(createduser.getId());
        return userdto;
    }

    @Override
    public UserDto createdAdmin(SignUpRequest signuprequest) {

        User user = new User();
        user.setName(signuprequest.getName());
        user.setEmail(signuprequest.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(signuprequest.getPassword()));
        user.setUserRole(UserRole.Admin);
        User createduser  =userRepository.save(user);
        UserDto userdto = new UserDto();
        userdto.setId(createduser.getId());
        return userdto;
    }

    @Override
    public boolean hasAdminwithemail(String email) {
        return false;
    }




}
