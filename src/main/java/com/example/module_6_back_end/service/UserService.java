package com.example.module_6_back_end.service;

import com.example.module_6_back_end.dto.ChangePasswordRequest;
import com.example.module_6_back_end.model.Staff;
import com.example.module_6_back_end.model.User;

public interface UserService {
    void changePassword(ChangePasswordRequest request) throws Exception;

    User getCurrentUser();

    User getUserByStaff(Staff staff);

    void deleteUser(User user);

    void isAdmin();
}
