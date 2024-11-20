package com.example.module_6_back_end.repository;

import com.example.module_6_back_end.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.email = :username OR u.username = :username OR u.phone = :username")
    User findByEmailOrUsernameOrPhone(String username);
}
