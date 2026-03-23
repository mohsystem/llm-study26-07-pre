package com.um.springbootprojstructure.service;

import com.um.springbootprojstructure.dto.PasswordValidationResultResponse;

public interface PasswordPolicyService {
    PasswordValidationResultResponse validatePassword(String password, String emailOrNull);
}
