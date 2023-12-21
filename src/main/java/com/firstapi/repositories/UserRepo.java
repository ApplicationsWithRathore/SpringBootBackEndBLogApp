package com.firstapi.repositories;

import com.firstapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UserRepo extends JpaRepository<User,Integer> {
Optional<User> findByEmail(String email);

}
