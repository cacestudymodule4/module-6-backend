package com.example.module_6_back_end.service;

import com.example.module_6_back_end.dto.ChangePasswordRequest;

public interface UserService {
    void changePassword(ChangePasswordRequest request) throws Exception;
}
