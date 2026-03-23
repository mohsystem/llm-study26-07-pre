package com.um.springbootprojstructure.dto;

import java.util.ArrayList;
import java.util.List;

public class PasswordValidationResultResponse {

    public enum Decision {
        ACCEPTED,
        REJECTED
    }

    public static class Violation {
        private String code;      // deterministic code
        private String message;   // human-readable (still deterministic strings here)

        public Violation() {}

        public Violation(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() { return code; }
        public String getMessage() { return message; }

        public void setCode(String code) { this.code = code; }
        public void setMessage(String message) { this.message = message; }
    }

    private Decision decision;
    private List<Violation> violations = new ArrayList<>();

    public PasswordValidationResultResponse() {}

    public PasswordValidationResultResponse(Decision decision, List<Violation> violations) {
        this.decision = decision;
        this.violations = violations;
    }

    public Decision getDecision() { return decision; }
    public List<Violation> getViolations() { return violations; }

    public void setDecision(Decision decision) { this.decision = decision; }
    public void setViolations(List<Violation> violations) { this.violations = violations; }
}
