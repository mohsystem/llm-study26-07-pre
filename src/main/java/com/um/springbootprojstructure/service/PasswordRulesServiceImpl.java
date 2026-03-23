package com.um.springbootprojstructure.service;

import com.um.springbootprojstructure.dto.PasswordRulesRequest;
import com.um.springbootprojstructure.dto.PasswordRulesResponse;
import com.um.springbootprojstructure.entity.PasswordRules;
import com.um.springbootprojstructure.repository.PasswordRulesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PasswordRulesServiceImpl implements PasswordRulesService {

    private static final long ACTIVE_RULES_ID = 1L;

    private final PasswordRulesRepository repo;

    public PasswordRulesServiceImpl(PasswordRulesRepository repo) {
        this.repo = repo;
    }

    @Override
    @Transactional(readOnly = true)
    public PasswordRulesResponse getActiveRules() {
        PasswordRules rules = repo.findById(ACTIVE_RULES_ID).orElseGet(() -> {
            PasswordRules r = new PasswordRules();
            r.setId(ACTIVE_RULES_ID);
            return repo.save(r);
        });
        return toResponse(rules);
    }

    @Override
    public PasswordRulesResponse updateRules(PasswordRulesRequest request) {
        PasswordRules rules = repo.findById(ACTIVE_RULES_ID).orElseGet(() -> {
            PasswordRules r = new PasswordRules();
            r.setId(ACTIVE_RULES_ID);
            return r;
        });

        rules.setMinLength(request.getMinLength());
        rules.setRequireUppercase(request.getRequireUppercase());
        rules.setRequireLowercase(request.getRequireLowercase());
        rules.setRequireDigit(request.getRequireDigit());
        rules.setRequireSpecial(request.getRequireSpecial());
        rules.setAllowedSpecials(request.getAllowedSpecials());
        rules.setDisallowCommonPasswords(request.getDisallowCommonPasswords());
        rules.setDisallowEmailAsPassword(request.getDisallowEmailAsPassword());

        PasswordRules saved = repo.save(rules);
        return toResponse(saved);
    }

    private static PasswordRulesResponse toResponse(PasswordRules r) {
        PasswordRulesResponse resp = new PasswordRulesResponse();
        resp.setMinLength(r.getMinLength());
        resp.setRequireUppercase(r.isRequireUppercase());
        resp.setRequireLowercase(r.isRequireLowercase());
        resp.setRequireDigit(r.isRequireDigit());
        resp.setRequireSpecial(r.isRequireSpecial());
        resp.setAllowedSpecials(r.getAllowedSpecials());
        resp.setDisallowCommonPasswords(r.isDisallowCommonPasswords());
        resp.setDisallowEmailAsPassword(r.isDisallowEmailAsPassword());
        resp.setUpdatedAt(r.getUpdatedAt());
        return resp;
    }
}
