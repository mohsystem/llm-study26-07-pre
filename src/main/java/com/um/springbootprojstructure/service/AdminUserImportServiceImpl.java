package com.um.springbootprojstructure.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.um.springbootprojstructure.dto.ImportUsersResponse;
import com.um.springbootprojstructure.dto.legacy.LegacyUserXml;
import com.um.springbootprojstructure.dto.legacy.LegacyUsersXml;
import com.um.springbootprojstructure.entity.Role;
import com.um.springbootprojstructure.entity.User;
import com.um.springbootprojstructure.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.Instant;
import java.util.Locale;

@Service
public class AdminUserImportServiceImpl implements AdminUserImportService {

    private final UserRepository userRepository;
    private final XmlMapper xmlMapper;

    public AdminUserImportServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.xmlMapper = new XmlMapper();
    }

    @Override
    @Transactional
    public ImportUsersResponse importLegacyUsersXml(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("XML file is required");
        }
        String contentType = file.getContentType();
        // contentType can be null; don't over-restrict, but do basic check if present
        if (contentType != null && !contentType.toLowerCase(Locale.ROOT).contains("xml")) {
            // still allow if user uploads with wrong content type; comment out if too strict
        }

        LegacyUsersXml legacy;
        try (InputStream in = file.getInputStream()) {
            legacy = xmlMapper.readValue(in, LegacyUsersXml.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid XML format: " + e.getMessage());
        }

        ImportUsersResponse resp = new ImportUsersResponse();
        resp.setImportedAt(Instant.now());

        if (legacy == null || legacy.getUsers() == null) {
            resp.setTotalRecords(0);
            return resp;
        }

        resp.setTotalRecords(legacy.getUsers().size());

        int idx = 0;
        for (LegacyUserXml r : legacy.getUsers()) {
            idx++;

            // Validate fields
            String fullName = r == null ? null : trimToNull(r.getFullName());
            String email = r == null ? null : trimToNull(r.getEmail());
            String roleText = r == null ? null : trimToNull(r.getRole());
            Boolean active = r == null ? null : r.getActive();

            if (fullName == null) {
                resp.getRejectedRecords().add(new ImportUsersResponse.RejectedRecord(idx, email, "Missing fullName"));
                continue;
            }
            if (email == null || !isValidEmailBasic(email)) {
                resp.getRejectedRecords().add(new ImportUsersResponse.RejectedRecord(idx, email, "Missing/invalid email"));
                continue;
            }

            if (userRepository.existsByEmail(email)) {
                resp.getSkippedEmails().add(email);
                continue;
            }

            Role role = Role.USER;
            if (roleText != null) {
                try {
                    role = Role.valueOf(roleText.toUpperCase(Locale.ROOT));
                } catch (Exception ex) {
                    resp.getRejectedRecords().add(new ImportUsersResponse.RejectedRecord(idx, email, "Invalid role: " + roleText));
                    continue;
                }
            }

            boolean isActive = active == null || active;

            User user = new User(fullName, email, role, isActive);
            User saved = userRepository.save(user);

            resp.getImportedPublicRefs().add(saved.getPublicRef());
        }

        resp.setImported(resp.getImportedPublicRefs().size());
        resp.setSkipped(resp.getSkippedEmails().size());
        resp.setRejected(resp.getRejectedRecords().size());

        return resp;
    }

    private static String trimToNull(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }

    // lightweight check; you can replace with jakarta validation if you want stricter
    private static boolean isValidEmailBasic(String email) {
        return email.contains("@") && email.indexOf('@') > 0 && email.indexOf('@') < email.length() - 1;
    }
}
