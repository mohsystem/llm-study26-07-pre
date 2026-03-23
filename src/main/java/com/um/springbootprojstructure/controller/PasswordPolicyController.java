package com.um.springbootprojstructure.controller;

import com.um.springbootprojstructure.dto.PasswordCheckRequest;
import com.um.springbootprojstructure.dto.PasswordValidationResultResponse;
import com.um.springbootprojstructure.service.PasswordPolicyService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class PasswordPolicyController {

    private final PasswordPolicyService passwordPolicyService;

    public PasswordPolicyController(PasswordPolicyService passwordPolicyService) {
        this.passwordPolicyService = passwordPolicyService;
    }

    /**
     * Deterministic password acceptance/rejection response.
     * Useful for registration/change/reset confirmation.
     */
    @PostMapping("/password/check")
    public PasswordValidationResultResponse check(@Valid @RequestBody PasswordCheckRequest request) {
        return passwordPolicyService.validatePassword(request.getPassword(), request.getEmail());
    }
}
