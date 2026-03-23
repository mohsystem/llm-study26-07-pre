package com.um.springbootprojstructure.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class MergeUsersResponse {

    private String sourcePublicRef;
    private String targetPublicRef;

    private boolean sourceDeleted;
    private List<String> actions = new ArrayList<>();

    private Instant mergedAt;

    public String getSourcePublicRef() { return sourcePublicRef; }
    public String getTargetPublicRef() { return targetPublicRef; }
    public boolean isSourceDeleted() { return sourceDeleted; }
    public List<String> getActions() { return actions; }
    public Instant getMergedAt() { return mergedAt; }

    public void setSourcePublicRef(String sourcePublicRef) { this.sourcePublicRef = sourcePublicRef; }
    public void setTargetPublicRef(String targetPublicRef) { this.targetPublicRef = targetPublicRef; }
    public void setSourceDeleted(boolean sourceDeleted) { this.sourceDeleted = sourceDeleted; }
    public void setActions(List<String> actions) { this.actions = actions; }
    public void setMergedAt(Instant mergedAt) { this.mergedAt = mergedAt; }
}
