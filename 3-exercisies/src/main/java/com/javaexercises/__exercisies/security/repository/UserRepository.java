package com.javaexercises.__exercisies.security.repository;



import com.javaexercises.__exercisies.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional <User> findByEmail(String email);
}
