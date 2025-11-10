package com.notes.notes.repository;

import com.notes.notes.model.AppUserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUserModel, Long> {
    Optional<AppUserModel> findByUsername (String username);
    boolean existsByUsername (String username);
}
