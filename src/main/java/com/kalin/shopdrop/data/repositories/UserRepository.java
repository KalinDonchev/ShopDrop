package com.kalin.shopdrop.data.repositories;

import com.kalin.shopdrop.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUsername(String username);

    Optional<User> findByUsernameAndPassword(String username, String password);

    Optional<User> findByUsername(String username);
}
