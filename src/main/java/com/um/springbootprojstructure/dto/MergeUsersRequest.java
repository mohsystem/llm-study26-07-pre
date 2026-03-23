package com.um.springbootprojstructure.dto;

import jakarta.validation.constraints.NotBlank;

public class MergeUsersRequest {

    @NotBlank
    private String sourcePublicRef;

    @NotBlank
    private String targetPublicRef;

    public String getSourcePublicRef() { return sourcePublicRef; }
    public String getTargetPublicRef() { return targetPublicRef; }

    public void setSourcePublicRef(String sourcePublicRef) { this.sourcePublicRef = sourcePublicRef; }
    public void setTargetPublicRef(String targetPublicRef) { this.targetPublicRef = targetPublicRef; }
}
