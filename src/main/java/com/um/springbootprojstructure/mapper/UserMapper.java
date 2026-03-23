package com.um.springbootprojstructure.mapper;

import com.um.springbootprojstructure.dto.UserResponse;
import com.um.springbootprojstructure.entity.User;

public final class UserMapper {

    private UserMapper() {}

    public static UserResponse toResponse(User user) {
        UserResponse r = new UserResponse();
        r.setId(user.getId());
        r.setPublicRef(user.getPublicRef());
        r.setFullName(user.getFullName());
        r.setEmail(user.getEmail());
        r.setRole(user.getRole());
        r.setActive(user.isActive());
        r.setCreatedAt(user.getCreatedAt());
        r.setUpdatedAt(user.getUpdatedAt());
        return r;
    }
}
