package com.example.module_6_back_end.repository;

import com.example.module_6_back_end.model.Staff;
import com.example.module_6_back_end.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.staff.email = :username OR u.username = :username OR u.staff.phone = :username")
    User findByEmailOrUsernameOrPhone(String username);

    User findByStaff(Staff staff);
}
