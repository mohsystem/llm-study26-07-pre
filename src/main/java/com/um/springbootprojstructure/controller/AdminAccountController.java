package com.um.springbootprojstructure.controller;

import com.um.springbootprojstructure.dto.PasswordRulesRequest;
import com.um.springbootprojstructure.dto.PasswordRulesResponse;
import com.um.springbootprojstructure.service.PasswordRulesService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/accounts")
public class AdminAccountController {

    private final PasswordRulesService passwordRulesService;

    public AdminAccountController(PasswordRulesService passwordRulesService) {
        this.passwordRulesService = passwordRulesService;
    }

    @GetMapping("/password-rules")
    @PreAuthorize("hasRole('ADMIN')")
    public PasswordRulesResponse getRules() {
        return passwordRulesService.getActiveRules();
    }

    @PutMapping("/password-rules")
    @PreAuthorize("hasRole('ADMIN')")
    public PasswordRulesResponse updateRules(@Valid @RequestBody PasswordRulesRequest request) {
        return passwordRulesService.updateRules(request);
    }
}
