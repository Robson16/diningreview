package com.codeacademy.diningreview.repository;

import com.codeacademy.diningreview.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByDisplayName(String displayName);

    boolean existsByDisplayName(String displayName);
}
