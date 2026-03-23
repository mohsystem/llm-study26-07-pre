package com.um.springbootprojstructure.dto;

import java.time.Instant;

public class PasswordRulesResponse {
    private int minLength;
    private boolean requireUppercase;
    private boolean requireLowercase;
    private boolean requireDigit;
    private boolean requireSpecial;
    private String allowedSpecials;
    private boolean disallowCommonPasswords;
    private boolean disallowEmailAsPassword;
    private Instant updatedAt;

    public int getMinLength() { return minLength; }
    public boolean isRequireUppercase() { return requireUppercase; }
    public boolean isRequireLowercase() { return requireLowercase; }
    public boolean isRequireDigit() { return requireDigit; }
    public boolean isRequireSpecial() { return requireSpecial; }
    public String getAllowedSpecials() { return allowedSpecials; }
    public boolean isDisallowCommonPasswords() { return disallowCommonPasswords; }
    public boolean isDisallowEmailAsPassword() { return disallowEmailAsPassword; }
    public Instant getUpdatedAt() { return updatedAt; }

    public void setMinLength(int minLength) { this.minLength = minLength; }
    public void setRequireUppercase(boolean requireUppercase) { this.requireUppercase = requireUppercase; }
    public void setRequireLowercase(boolean requireLowercase) { this.requireLowercase = requireLowercase; }
    public void setRequireDigit(boolean requireDigit) { this.requireDigit = requireDigit; }
    public void setRequireSpecial(boolean requireSpecial) { this.requireSpecial = requireSpecial; }
    public void setAllowedSpecials(String allowedSpecials) { this.allowedSpecials = allowedSpecials; }
    public void setDisallowCommonPasswords(boolean disallowCommonPasswords) { this.disallowCommonPasswords = disallowCommonPasswords; }
    public void setDisallowEmailAsPassword(boolean disallowEmailAsPassword) { this.disallowEmailAsPassword = disallowEmailAsPassword; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
