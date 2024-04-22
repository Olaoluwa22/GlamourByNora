package com.GlamourByNora.api.controller;

import com.GlamourByNora.api.dto.AdminDto;
import com.GlamourByNora.api.model.User;
import com.GlamourByNora.api.repository.UserRepository;
import com.GlamourByNora.api.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminService adminService;
    @PostMapping("/create-admin-profile")
    public ResponseEntity<?> createAdminProfile(@Valid @RequestBody AdminDto adminDto){
        return adminService.createAdminProfile(adminDto);
    }
    @GetMapping("/{id}")
    public List<User> getAdminById(@PathVariable(name ="id") Long id){
        return adminService.getAdminById(id);
    }
    @GetMapping("/getAllAdmins")
    public List<User> getAllAdmins(){
        return adminService.getAllAdmins();
    }
    @GetMapping("/getAdminByPageable")
    public Page<User> getAdminByPageable(@RequestParam int page, @RequestParam int size){
        return adminService.getAdminByPageable(page, size);
    }
}