package com.um.springbootprojstructure.service;

import com.um.springbootprojstructure.config.LdapProperties;
import com.um.springbootprojstructure.dto.ValidateDirectoryRequest;
import com.um.springbootprojstructure.dto.ValidateDirectoryResponse;
import jakarta.naming.NamingEnumeration;
import jakarta.naming.directory.Attribute;
import jakarta.naming.directory.Attributes;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.HardcodedFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class DirectoryValidationServiceImpl implements DirectoryValidationService {

    private final LdapTemplate ldapTemplate;
    private final LdapProperties props;

    public DirectoryValidationServiceImpl(LdapTemplate ldapTemplate, LdapProperties props) {
        this.ldapTemplate = ldapTemplate;
        this.props = props;
    }

    @Override
    @Transactional(readOnly = true)
    public ValidateDirectoryResponse validate(ValidateDirectoryRequest request) {
        if (!props.isEnabled()) {
            throw new IllegalStateException("LDAP validation is disabled (app.ldap.enabled=false)");
        }

        String username = trimToNull(request.getUsername());
        String email = trimToNull(request.getEmail());
        String employeeId = trimToNull(request.getEmployeeId());

        if (username == null && email == null && employeeId == null) {
            throw new IllegalArgumentException("Provide at least one identity attribute (username, email, employeeId)");
        }

        // Default filter supports username/email; optionally add employeeId if provided.
        // We'll build a safe filter string by escaping values.
        // Base filter from properties is expected to use {0} and {1} placeholders.
        String baseFilter = props.getUserSearchFilter();
        String filter = baseFilter;

        // crude placeholder fill with LDAP-escaped values
        filter = filter.replace("{0}", ldapEscape(username == null ? "" : username));
        filter = filter.replace("{1}", ldapEscape(email == null ? "" : email));

        if (employeeId != null) {
            // Wrap with employeeId condition as well
            filter = "(&" + filter + "(employeeNumber=" + ldapEscape(employeeId) + "))";
        }

        List<Map<String, Object>> matches = ldapTemplate.search(
                props.getUserSearchBase(),
                new HardcodedFilter(filter).encode(),
                (AttributesMapper<Map<String, Object>>) DirectoryValidationServiceImpl::attributesToMap
        );

        ValidateDirectoryResponse resp = new ValidateDirectoryResponse();
        resp.setMatchCount(matches.size());
        resp.setMatched(!matches.isEmpty());
        if (!matches.isEmpty()) {
            resp.setRecord(matches.get(0)); // return first match
        }
        return resp;
    }

    private static Map<String, Object> attributesToMap(Attributes attrs) {
        Map<String, Object> map = new LinkedHashMap<>();
        try {
            NamingEnumeration<? extends Attribute> all = attrs.getAll();
            while (all.hasMore()) {
                Attribute a = all.next();
                List<Object> values = new ArrayList<>();
                NamingEnumeration<?> ve = a.getAll();
                while (ve.hasMore()) values.add(ve.next());
                map.put(a.getID(), values.size() == 1 ? values.get(0) : values);
            }
        } catch (Exception e) {
            // ignore mapping errors
        }
        return map;
    }

    private static String trimToNull(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }

    /**
     * Minimal LDAP filter escaping.
     */
    private static String ldapEscape(String value) {
        if (value == null) return "";
        return value
                .replace("\\", "\\5c")
                .replace("*", "\\2a")
                .replace("(", "\\28")
                .replace(")", "\\29")
                .replace("\u0000", "\\00");
    }
}
