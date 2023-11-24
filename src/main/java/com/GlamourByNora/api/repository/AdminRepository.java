package com.GlamourByNora.api.repository;

import com.GlamourByNora.api.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
