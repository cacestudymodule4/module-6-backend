package com.example.module_6_back_end.repository;

import com.example.module_6_back_end.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByPhone(String phone);

    boolean existsByIdentification(String identification);

    @Query("SELECT u FROM User u WHERE u.email = :username OR u.username = :username")
    User findByEmailOrUsername(String username);
}
