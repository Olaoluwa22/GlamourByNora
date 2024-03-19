package com.GlamourByNora.api.repository;

import com.GlamourByNora.api.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUserByEmail(String email);
    List<User> findUserByDeleted(boolean deleted);
    Page<User> findUserByDeletedFalse(Pageable pageable);
    Optional<User> findUserByEmailAndDeleted(String email, boolean deleted);
}
