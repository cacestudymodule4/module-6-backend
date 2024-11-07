package com.example.module_6_back_end.repository;

import com.example.module_6_back_end.model.Role;
import com.example.module_6_back_end.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByUser(User user);
}
