package com.um.springbootprojstructure.service;

import com.um.springbootprojstructure.dto.CreateUserRequest;
import com.um.springbootprojstructure.dto.MergeUsersResponse;
import com.um.springbootprojstructure.dto.UpdateUserProfileRequest;
import com.um.springbootprojstructure.dto.UpdateUserRequest;
import com.um.springbootprojstructure.entity.Role;
import com.um.springbootprojstructure.entity.User;
import com.um.springbootprojstructure.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + request.getEmail());
        }

        boolean active = request.getActive() == null || request.getActive();
        Role role = request.getRole() == null ? Role.USER : request.getRole();

        User user = new User(request.getFullName(), request.getEmail(), role, active);
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found. id=" + id));
    }

    @Override
    @Transactional(readOnly = true)
    public User getByPublicRef(String publicRef) {
        return userRepository.findByPublicRef(publicRef)
                .orElseThrow(() -> new IllegalArgumentException("User not found. publicRef=" + publicRef));
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> list() {
        return userRepository.findAll();
    }

    @Override
    public User update(Long id, UpdateUserRequest request) {
        User user = getById(id);

        if (request.getEmail() != null && !request.getEmail().equalsIgnoreCase(user.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new IllegalArgumentException("Email already exists: " + request.getEmail());
            }
            user.setEmail(request.getEmail());
        }
        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }
        if (request.getRole() != null) {
            user.setRole(request.getRole());
        }
        if (request.getActive() != null) {
            user.setActive(request.getActive());
        }

        return userRepository.save(user);
    }

    @Override
    public User updateProfileByPublicRef(String publicRef, UpdateUserProfileRequest request) {
        User user = getByPublicRef(publicRef);

        if (request.getEmail() != null && !request.getEmail().equalsIgnoreCase(user.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new IllegalArgumentException("Email already exists: " + request.getEmail());
            }
            user.setEmail(request.getEmail());
        }

        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }

        // explicitly NOT allowing changes to role/active/publicRef here
        return userRepository.save(user);
    }

    @Override
    public MergeUsersResponse mergeUsers(String sourcePublicRef, String targetPublicRef) {
        if (sourcePublicRef.equals(targetPublicRef)) {
            throw new IllegalArgumentException("sourcePublicRef and targetPublicRef must be different");
        }

        User source = getByPublicRef(sourcePublicRef);
        User target = getByPublicRef(targetPublicRef);

        List<String> actions = new ArrayList<>();

        // Consolidate fullName (example rule: only fill missing on target)
        if ((target.getFullName() == null || target.getFullName().isBlank())
                && source.getFullName() != null
                && !source.getFullName().isBlank()) {
            target.setFullName(source.getFullName());
            actions.add("Copied fullName from source to target (target was blank).");
        }

        // Consolidate active: keep active if either active
        if (!target.isActive() && source.isActive()) {
            target.setActive(true);
            actions.add("Set target active=true because source was active.");
        }

        // Consolidate role: keep ADMIN if either is ADMIN
        if (source.getRole() == Role.ADMIN && target.getRole() != Role.ADMIN) {
            target.setRole(Role.ADMIN);
            actions.add("Upgraded target role to ADMIN because source was ADMIN.");
        }

        // Consolidate email: keep target email; if different, record
        if (source.getEmail() != null && target.getEmail() != null
                && !source.getEmail().equalsIgnoreCase(target.getEmail())) {
            actions.add("Kept target email; source email differed and was not copied.");
        } else if ((target.getEmail() == null || target.getEmail().isBlank())
                && source.getEmail() != null && !source.getEmail().isBlank()) {
            // rare case if target email missing
            if (userRepository.existsByEmail(source.getEmail())) {
                actions.add("Target email was blank but source email already exists elsewhere; not copied.");
            } else {
                target.setEmail(source.getEmail());
                actions.add("Copied email from source to target (target was blank).");
            }
        }

        // Persist target updates
        userRepository.save(target);

        // Delete source
        userRepository.delete(source);
        actions.add("Deleted source user account.");

        MergeUsersResponse resp = new MergeUsersResponse();
        resp.setSourcePublicRef(sourcePublicRef);
        resp.setTargetPublicRef(targetPublicRef);
        resp.setSourceDeleted(true);
        resp.setActions(actions);
        resp.setMergedAt(Instant.now());
        return resp;
    }

    @Override
    public User updateRoleById(Long id, Role role) {
        User user = getById(id);
        user.setRole(role);
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        User user = getById(id);
        userRepository.delete(user);
    }
}
