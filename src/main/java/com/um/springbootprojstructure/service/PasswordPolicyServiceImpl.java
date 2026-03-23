package com.um.springbootprojstructure.service;

import com.um.springbootprojstructure.dto.PasswordValidationResultResponse;
import com.um.springbootprojstructure.dto.PasswordValidationResultResponse.Decision;
import com.um.springbootprojstructure.dto.PasswordValidationResultResponse.Violation;
import com.um.springbootprojstructure.entity.PasswordRules;
import com.um.springbootprojstructure.repository.PasswordRulesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class PasswordPolicyServiceImpl implements PasswordPolicyService {

    // Deterministic violation codes
    public static final String CODE_MIN_LENGTH = "MIN_LENGTH";
    public static final String CODE_REQUIRE_UPPER = "REQUIRE_UPPERCASE";
    public static final String CODE_REQUIRE_LOWER = "REQUIRE_LOWERCASE";
    public static final String CODE_REQUIRE_DIGIT = "REQUIRE_DIGIT";
    public static final String CODE_REQUIRE_SPECIAL = "REQUIRE_SPECIAL";
    public static final String CODE_SPECIAL_NOT_ALLOWED = "SPECIAL_NOT_ALLOWED";
    public static final String CODE_PASSWORD_EQUALS_EMAIL = "PASSWORD_EQUALS_EMAIL";
    public static final String CODE_COMMON_PASSWORD = "COMMON_PASSWORD";

    private static final long ACTIVE_RULES_ID = 1L;

    private final PasswordRulesRepository rulesRepository;

    // Minimal deterministic list; replace/extend with a real dataset if needed.
    private static final Set<String> COMMON_PASSWORDS = Set.of(
            "password", "123456", "12345678", "qwerty", "letmein", "admin", "welcome"
    );

    public PasswordPolicyServiceImpl(PasswordRulesRepository rulesRepository) {
        this.rulesRepository = rulesRepository;
    }

    @Override
    public PasswordValidationResultResponse validatePassword(String password, String emailOrNull) {
        PasswordRules rules = rulesRepository.findById(ACTIVE_RULES_ID).orElseGet(PasswordRules::new);

        List<Violation> violations = new ArrayList<>();

        String pw = password == null ? "" : password;

        // 1) min length
        if (pw.length() < rules.getMinLength()) {
            violations.add(new Violation(
                    CODE_MIN_LENGTH,
                    "Password must be at least " + rules.getMinLength() + " characters long"
            ));
        }

        // 2) required character classes
        if (rules.isRequireUppercase() && pw.chars().noneMatch(Character::isUpperCase)) {
            violations.add(new Violation(CODE_REQUIRE_UPPER, "Password must contain an uppercase letter"));
        }
        if (rules.isRequireLowercase() && pw.chars().noneMatch(Character::isLowerCase)) {
            violations.add(new Violation(CODE_REQUIRE_LOWER, "Password must contain a lowercase letter"));
        }
        if (rules.isRequireDigit() && pw.chars().noneMatch(Character::isDigit)) {
            violations.add(new Violation(CODE_REQUIRE_DIGIT, "Password must contain a digit"));
        }

        // 3) special chars
        String allowedSpecials = rules.getAllowedSpecials() == null ? "" : rules.getAllowedSpecials();
        boolean hasAnySpecial = pw.chars().anyMatch(ch -> !Character.isLetterOrDigit(ch));
        boolean hasAllowedSpecial = pw.chars().anyMatch(ch -> allowedSpecials.indexOf(ch) >= 0);

        if (rules.isRequireSpecial() && !hasAllowedSpecial) {
            violations.add(new Violation(
                    CODE_REQUIRE_SPECIAL,
                    "Password must contain at least one special character from the allowed set"
            ));
        }

        // reject disallowed specials deterministically (if password includes specials not in allowed set)
        if (hasAnySpecial) {
            boolean hasDisallowed = pw.chars()
                    .filter(ch -> !Character.isLetterOrDigit(ch))
                    .anyMatch(ch -> allowedSpecials.indexOf(ch) < 0);

            if (hasDisallowed) {
                violations.add(new Violation(
                        CODE_SPECIAL_NOT_ALLOWED,
                        "Password contains a special character outside the allowed set"
                ));
            }
        }

        // 4) disallow email as password (simple equality check)
        if (rules.isDisallowEmailAsPassword() && emailOrNull != null) {
            String e = emailOrNull.trim();
            if (!e.isEmpty() && pw.equalsIgnoreCase(e)) {
                violations.add(new Violation(CODE_PASSWORD_EQUALS_EMAIL, "Password must not equal the email address"));
            }
        }

        // 5) common password (simple deterministic list)
        if (rules.isDisallowCommonPasswords()) {
            String normalized = pw.trim().toLowerCase(Locale.ROOT);
            if (COMMON_PASSWORDS.contains(normalized)) {
                violations.add(new Violation(CODE_COMMON_PASSWORD, "Password is too common"));
            }
        }

        // deterministic ordering (by code, then message)
        violations.sort(Comparator.comparing(Violation::getCode).thenComparing(Violation::getMessage));

        Decision decision = violations.isEmpty() ? Decision.ACCEPTED : Decision.REJECTED;
        return new PasswordValidationResultResponse(decision, violations);
    }
}
