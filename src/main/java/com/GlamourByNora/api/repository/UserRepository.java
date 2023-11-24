package com.GlamourByNora.api.repository;

import com.GlamourByNora.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
