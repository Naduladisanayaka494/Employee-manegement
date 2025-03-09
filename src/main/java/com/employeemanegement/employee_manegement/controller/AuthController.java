package com.employeemanegement.employee_manegement.controller;


import com.employeemanegement.employee_manegement.dto.AuthenticationRequest;
import com.employeemanegement.employee_manegement.dto.AuthenticationResponse;
import com.employeemanegement.employee_manegement.dto.SignUpRequest;
import com.employeemanegement.employee_manegement.dto.UserDto;
import com.employeemanegement.employee_manegement.entity.Department;
import com.employeemanegement.employee_manegement.entity.User;
import com.employeemanegement.employee_manegement.repositories.DepartmentRepository;
import com.employeemanegement.employee_manegement.repositories.UserRepository;
import com.employeemanegement.employee_manegement.services.auth.AuthServiceImpl;
import com.employeemanegement.employee_manegement.services.jwt.UserServiceImpl;
import com.employeemanegement.employee_manegement.utill.JWTUtill;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController

@RequestMapping("/api/auth")

public class AuthController {

    private final AuthServiceImpl authService;
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;
    private final JWTUtill jwtUtill;
    private  final UserRepository userRepository;

    private final DepartmentRepository departmentRepository;


    public AuthController(AuthServiceImpl authService, AuthenticationManager authenticationManager, UserServiceImpl userService, JWTUtill jwtUtill, UserRepository userRepository, DepartmentRepository departmentRepository) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtill = jwtUtill;
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
    }

//    @PostMapping("/signup")
//    public ResponseEntity<?> signup(@RequestBody SignUpRequest signupRequest) {
//        if(authService.hasAdminwithemail(signupRequest.getEmail()))
//            return new ResponseEntity<>("email already exists",HttpStatus.NOT_ACCEPTABLE);
//        UserDto createduserdto  =authService.createEmployee(signupRequest);
//        if(createduserdto==null) return new ResponseEntity<>(
//                "Admin not created", HttpStatus.BAD_REQUEST
//        );
//        return new ResponseEntity<>(createduserdto,HttpStatus.CREATED);
//
//    }

//    @PostMapping("/signup/admin")
//    public ResponseEntity<?> signupAdmin(@RequestBody SignUpRequest signupRequest) {
//        if(authService.hasAdminwithemail(signupRequest.getEmail()))
//            return new ResponseEntity<>("email already exists",HttpStatus.NOT_ACCEPTABLE);
//        UserDto createduserdto  =authService.createdAdmin(signupRequest);
//        if(createduserdto==null) return new ResponseEntity<>(
//                "Admin not created", HttpStatus.BAD_REQUEST
//        );
//        return new ResponseEntity<>(createduserdto,HttpStatus.CREATED);
//
//    }

    @PostMapping("/login")
    public AuthenticationResponse createauthenticationtoken(@RequestBody AuthenticationRequest authenticationRequest) throws BadCredentialsException, DisabledException, UsernameNotFoundException, BadRequestException {
        System.out.print("Hi-Login");
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),authenticationRequest.getPassword()));
//        }catch(BadCredentialsException e){
//            throw new BadRequestException("incorrect username or passoword");
//        }
        final UserDetails userDetails= userService.userDetailService().loadUserByUsername(authenticationRequest.getEmail());
        System.out.print(userDetails.getUsername());
        Optional<User> optionalUser = userRepository.findByEmail(userDetails.getUsername());
        System.out.print( optionalUser);
        final String jwt = jwtUtill.generateToken(userDetails);
        AuthenticationResponse authenticationResponse= new AuthenticationResponse();
        if(optionalUser.isPresent()){
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
        }
        return authenticationResponse;

    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream().map(user -> {
            UserDto userDto = new UserDto();
            userDto.setId((user.getId()));
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());
            userDto.setUserRole(user.getUserRole());
            userDto.setProfilePhotoPath(user.getProfilePhotoPath());
            return userDto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(userDtos);
    }

    @PostMapping("/{userId}/assign-department/{departmentId}")
    public ResponseEntity<User> assignDepartmentToUser(@PathVariable String userId, @PathVariable String departmentId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Department> departmentOptional = departmentRepository.findById(departmentId);

        if (userOptional.isPresent() && departmentOptional.isPresent()) {
            User user = userOptional.get();
            Department department = departmentOptional.get();
            user.setDepartment(department);
            userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


//    @PostMapping("/signup")
//    public ResponseEntity<?> signup(@RequestBody SignUpRequest signupRequest, @RequestParam(value = "profilePhoto", required = false) MultipartFile profilePhoto) {
//        if (authService.hasAdminwithemail(signupRequest.getEmail()))
//            return new ResponseEntity<>("email already exists", HttpStatus.NOT_ACCEPTABLE);
//        UserDto createduserdto = authService.createEmployee(signupRequest);
//
//        if (createduserdto == null) return new ResponseEntity<>(
//                "Admin not created", HttpStatus.BAD_REQUEST
//        );
//
//        if (profilePhoto != null && !profilePhoto.isEmpty()) {
//            try {
//                String profilePhotoPath = saveProfilePhoto(profilePhoto, createduserdto.getId());
//                User user = userRepository.findById(createduserdto.getId()).orElse(null);
//                if (user != null) {
//                    user.setProfilePhotoPath(profilePhotoPath);
//                    userRepository.save(user);
//                }
//            } catch (IOException e) {
//                return new ResponseEntity<>("Failed to save profile photo", HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        }
//
//        return new ResponseEntity<>(createduserdto, HttpStatus.CREATED);
//    }
//
//    @PostMapping("/signup/admin")
//    public ResponseEntity<?> signupAdmin(@RequestBody SignUpRequest signupRequest, @RequestParam(value = "profilePhoto", required = false) MultipartFile profilePhoto) {
//        if (authService.hasAdminwithemail(signupRequest.getEmail()))
//            return new ResponseEntity<>("email already exists", HttpStatus.NOT_ACCEPTABLE);
//        UserDto createduserdto = authService.createdAdmin(signupRequest);
//        if (createduserdto == null) return new ResponseEntity<>(
//                "Admin not created", HttpStatus.BAD_REQUEST
//        );
//
//        if (profilePhoto != null && !profilePhoto.isEmpty()) {
//            try {
//                String profilePhotoPath = saveProfilePhoto(profilePhoto, createduserdto.getId());
//                User user = userRepository.findById(createduserdto.getId()).orElse(null);
//                if (user != null) {
//                    user.setProfilePhotoPath(profilePhotoPath);
//                    userRepository.save(user);
//                }
//
//            } catch (IOException e) {
//                return new ResponseEntity<>("Failed to save profile photo", HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        }
//
//        return new ResponseEntity<>(createduserdto, HttpStatus.CREATED);
//    }
@PostMapping("/signup")
public ResponseEntity<?> signup(@ModelAttribute SignUpRequest signupRequest) { // use @ModelAttribute
    if (authService.hasAdminwithemail(signupRequest.getEmail()))
        return new ResponseEntity<>("email already exists", HttpStatus.NOT_ACCEPTABLE);
    UserDto createduserdto = authService.createEmployee(signupRequest);

    if (createduserdto == null) return new ResponseEntity<>(
            "Admin not created", HttpStatus.BAD_REQUEST
    );

    if (signupRequest.getProfilePhoto() != null && !signupRequest.getProfilePhoto().isEmpty()) {
        try {
            String profilePhotoPath = saveProfilePhoto(signupRequest.getProfilePhoto(), createduserdto.getId());
            User user = userRepository.findById(createduserdto.getId()).orElse(null);
            if (user != null) {
                user.setProfilePhotoPath(profilePhotoPath);
                userRepository.save(user);
            }
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to save profile photo", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    return new ResponseEntity<>(createduserdto, HttpStatus.CREATED);
}

    @PostMapping("/signup/admin")
    public ResponseEntity<?> signupAdmin(@ModelAttribute SignUpRequest signupRequest) { // use @ModelAttribute
        if (authService.hasAdminwithemail(signupRequest.getEmail()))
            return new ResponseEntity<>("email already exists", HttpStatus.NOT_ACCEPTABLE);
        UserDto createduserdto = authService.createdAdmin(signupRequest);
        if (createduserdto == null) return new ResponseEntity<>(
                "Admin not created", HttpStatus.BAD_REQUEST
        );

        if (signupRequest.getProfilePhoto() != null && !signupRequest.getProfilePhoto().isEmpty()) {
            try {
                String profilePhotoPath = saveProfilePhoto(signupRequest.getProfilePhoto(), createduserdto.getId());
                User user = userRepository.findById(createduserdto.getId()).orElse(null);
                if (user != null) {
                    user.setProfilePhotoPath(profilePhotoPath);
                    userRepository.save(user);
                }

            } catch (IOException e) {
                return new ResponseEntity<>("Failed to save profile photo", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>(createduserdto, HttpStatus.CREATED);
    }
    private String saveProfilePhoto(MultipartFile profilePhoto, String userId) throws IOException {
        String uploadDir = "uploads/profile_photos/";
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = userId + "_" + profilePhoto.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(profilePhoto.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return filePath.toString();
    }
}
