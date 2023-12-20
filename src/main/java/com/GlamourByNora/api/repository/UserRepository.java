package com.GlamourByNora.api.repository;

import com.GlamourByNora.api.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    List<User> findByDeleted(boolean deleted);
    Page<User> findByDeletedFalse(Pageable pageable);
    Optional<User> findByEmailAndDeleted(String email, boolean deleted);
}
