package com.um.springbootprojstructure.controller;

import com.um.springbootprojstructure.dto.*;
import com.um.springbootprojstructure.service.AdminUserImportService;
import com.um.springbootprojstructure.service.DirectoryValidationService;
import com.um.springbootprojstructure.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private final UserService userService;
    private final AdminUserImportService adminUserImportService;
    private final DirectoryValidationService directoryValidationService;

    public AdminUserController(UserService userService,
                               AdminUserImportService adminUserImportService,
                               DirectoryValidationService directoryValidationService) {
        this.userService = userService;
        this.adminUserImportService = adminUserImportService;
        this.directoryValidationService = directoryValidationService;
    }

    @PostMapping("/merge")
    @PreAuthorize("hasRole('ADMIN')")
    public MergeUsersResponse merge(@Valid @RequestBody MergeUsersRequest request) {
        return userService.mergeUsers(request.getSourcePublicRef(), request.getTargetPublicRef());
    }

    @PutMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public UpdateUserRoleResponse updateRole(@PathVariable Long id,
                                             @Valid @RequestBody UpdateUserRoleRequest request) {
        var updated = userService.updateRoleById(id, request.getRole());

        UpdateUserRoleResponse resp = new UpdateUserRoleResponse();
        resp.setId(updated.getId());
        resp.setPublicRef(updated.getPublicRef());
        resp.setRole(updated.getRole());
        resp.setUpdatedAt(updated.getUpdatedAt());
        return resp;
    }

    @PostMapping(value = "/import-xml", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ImportUsersResponse importXml(@RequestPart("file") MultipartFile file) {
        return adminUserImportService.importLegacyUsersXml(file);
    }

    @PostMapping("/validate-directory")
    @PreAuthorize("hasRole('ADMIN')")
    public ValidateDirectoryResponse validateDirectory(@Valid @RequestBody ValidateDirectoryRequest request) {
        return directoryValidationService.validate(request);
    }
}
