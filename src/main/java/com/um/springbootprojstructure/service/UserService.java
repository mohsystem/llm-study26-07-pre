package com.um.springbootprojstructure.service;

import com.um.springbootprojstructure.dto.CreateUserRequest;
import com.um.springbootprojstructure.dto.MergeUsersResponse;
import com.um.springbootprojstructure.dto.UpdateUserProfileRequest;
import com.um.springbootprojstructure.dto.UpdateUserRequest;
import com.um.springbootprojstructure.entity.Role;
import com.um.springbootprojstructure.entity.User;

import java.util.List;

public interface UserService {
    User create(CreateUserRequest request);
    User getById(Long id);
    User getByPublicRef(String publicRef);
    List<User> list();
    User update(Long id, UpdateUserRequest request);
    User updateProfileByPublicRef(String publicRef, UpdateUserProfileRequest request);
    MergeUsersResponse mergeUsers(String sourcePublicRef, String targetPublicRef);
    User updateRoleById(Long id, Role role);
    void delete(Long id);
}
