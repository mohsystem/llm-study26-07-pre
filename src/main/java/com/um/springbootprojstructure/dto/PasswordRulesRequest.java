package com.um.springbootprojstructure.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PasswordRulesRequest {

    @NotNull
    @Min(6)
    @Max(128)
    private Integer minLength;

    @NotNull
    private Boolean requireUppercase;

    @NotNull
    private Boolean requireLowercase;

    @NotNull
    private Boolean requireDigit;

    @NotNull
    private Boolean requireSpecial;

    @NotBlank
    private String allowedSpecials;

    @NotNull
    private Boolean disallowCommonPasswords;

    @NotNull
    private Boolean disallowEmailAsPassword;

    public Integer getMinLength() { return minLength; }
    public Boolean getRequireUppercase() { return requireUppercase; }
    public Boolean getRequireLowercase() { return requireLowercase; }
    public Boolean getRequireDigit() { return requireDigit; }
    public Boolean getRequireSpecial() { return requireSpecial; }
    public String getAllowedSpecials() { return allowedSpecials; }
    public Boolean getDisallowCommonPasswords() { return disallowCommonPasswords; }
    public Boolean getDisallowEmailAsPassword() { return disallowEmailAsPassword; }

    public void setMinLength(Integer minLength) { this.minLength = minLength; }
    public void setRequireUppercase(Boolean requireUppercase) { this.requireUppercase = requireUppercase; }
    public void setRequireLowercase(Boolean requireLowercase) { this.requireLowercase = requireLowercase; }
    public void setRequireDigit(Boolean requireDigit) { this.requireDigit = requireDigit; }
    public void setRequireSpecial(Boolean requireSpecial) { this.requireSpecial = requireSpecial; }
    public void setAllowedSpecials(String allowedSpecials) { this.allowedSpecials = allowedSpecials; }
    public void setDisallowCommonPasswords(Boolean disallowCommonPasswords) { this.disallowCommonPasswords = disallowCommonPasswords; }
    public void setDisallowEmailAsPassword(Boolean disallowEmailAsPassword) { this.disallowEmailAsPassword = disallowEmailAsPassword; }
}
