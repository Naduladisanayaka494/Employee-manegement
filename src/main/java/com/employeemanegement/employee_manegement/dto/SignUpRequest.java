package com.employeemanegement.employee_manegement.dto;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SignUpRequest {
    private String name;
    private String email;
    private String password;
    private MultipartFile profilePhoto; // Added profile photo field

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MultipartFile getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(MultipartFile profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
}
