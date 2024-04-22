package com.GlamourByNora.api.service;

import com.GlamourByNora.api.dto.AdminDto;
import com.GlamourByNora.api.model.User;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {
    ResponseEntity<?> createAdminProfile(AdminDto adminDto);
    List<User> getAdminById(Long id);
    List<User> getAllAdmins();
    Page<User> getAdminByPageable(int page, int size);
}