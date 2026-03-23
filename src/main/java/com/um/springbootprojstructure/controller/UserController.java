package com.um.springbootprojstructure.controller;

import com.um.springbootprojstructure.dto.CreateUserRequest;
import com.um.springbootprojstructure.dto.UpdateUserProfileRequest;
import com.um.springbootprojstructure.dto.UpdateUserRequest;
import com.um.springbootprojstructure.dto.UserResponse;
import com.um.springbootprojstructure.mapper.UserMapper;
import com.um.springbootprojstructure.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponse create(@Valid @RequestBody CreateUserRequest request) {
        return UserMapper.toResponse(userService.create(request));
    }

    /**
     * Required by spec:
     * GET /api/users/{publicRef}
     */
    @GetMapping("/{publicRef}")
    public UserResponse getByPublicRef(@PathVariable String publicRef) {
        return UserMapper.toResponse(userService.getByPublicRef(publicRef));
    }

    /**
     * Required by spec:
     * PUT /api/users/{publicRef}
     */
    @PutMapping("/{publicRef}")
    public UserResponse updateProfile(@PathVariable String publicRef,
                                      @Valid @RequestBody UpdateUserProfileRequest request) {
        return UserMapper.toResponse(userService.updateProfileByPublicRef(publicRef, request));
    }

    /**
     * Optional internal endpoint (avoids path ambiguity).
     */
    @GetMapping("/id/{id}")
    public UserResponse getById(@PathVariable Long id) {
        return UserMapper.toResponse(userService.getById(id));
    }

    @GetMapping
    public List<UserResponse> list() {
        return userService.list().stream().map(UserMapper::toResponse).toList();
    }

    @PutMapping("/id/{id}")
    public UserResponse updateInternal(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) {
        return UserMapper.toResponse(userService.update(id, request));
    }

    @DeleteMapping("/id/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
