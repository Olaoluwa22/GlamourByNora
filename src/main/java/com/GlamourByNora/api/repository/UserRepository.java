package com.GlamourByNora.api.repository;

import com.GlamourByNora.api.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUserByEmail(String email);
    List<User> findUserByDeleted(boolean isDeleted);
    Page<User> findUserByDeletedFalse(Pageable pageable);
    Optional<User> findUserByEmailAndDeleted(String email, boolean isDeleted);
    List<User> findUserByRole(String role);
    List<User> findUserByIdAndRole(Long id, String role);
    Page<User> findUserByRole(String role, Pageable pageable);
}
