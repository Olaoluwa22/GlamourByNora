package com.GlamourByNora.api.controller;

import com.GlamourByNora.api.dto.AdminDto;
import com.GlamourByNora.api.model.Admin;
import com.GlamourByNora.api.repository.AdminRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;
    @GetMapping("/admins")
    public List<Admin> getUsers(){
        return adminRepository.findAll();
    }
    @GetMapping("/admin/get")
    public Page<Admin> getAdminByPageable(@RequestParam int page, @RequestParam int size){
        Pageable pageable = PageRequest.of(page,size);
        return adminRepository.findAll(pageable);
    }
    @GetMapping("/admin/{id}")
    public Optional<Admin> getUAdminbyId(@PathVariable(name ="id") Long adminId){
        return adminRepository.findById(adminId);
    }
    @PostMapping("/admin/create")
    public String createAdmin(@Valid @RequestBody AdminDto adminDto){
        Admin admin = new Admin();
        admin.setFirstname(adminDto.getFirstname());
        admin.setLastname(adminDto.getLastname());
        admin.setCountry(adminDto.getCountry());
        admin.setState(adminDto.getState());
        admin.setAddress(adminDto.getAddress());
        admin.setEmail(adminDto.getEmail());
        admin.setPhone_no(adminDto.getPhone_no());
        adminRepository.save(admin);
        return "New Admin created...";
    }
    @PutMapping("/admin/update/{id}")
    public String updateAdminInfo( @Valid @PathVariable(name="id")Long id, @RequestBody AdminDto adminDto){
        Admin admin = adminRepository.findById(id).get();
        admin.setFirstname(adminDto.getFirstname());
        admin.setLastname(adminDto.getLastname());
        admin.setCountry(adminDto.getCountry());
        admin.setState(adminDto.getState());
        admin.setAddress(adminDto.getAddress());
        admin.setEmail(adminDto.getEmail());
        admin.setPhone_no(adminDto.getPhone_no());
        adminRepository.save(admin);
        return "Admin info updated...";
    }
    @DeleteMapping("/admin/delete/{id}")
    public String deleteAdmin(@PathVariable(name ="id")Long id){
        adminRepository.deleteById(id);
        return "Admin"+id+"'s info has been deleted Successfully...";
    }
}
