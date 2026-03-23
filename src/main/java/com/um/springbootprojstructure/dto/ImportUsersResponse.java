package com.um.springbootprojstructure.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ImportUsersResponse {

    private int totalRecords;
    private int imported;
    private int skipped;
    private int rejected;

    private List<String> importedPublicRefs = new ArrayList<>();
    private List<String> skippedEmails = new ArrayList<>();
    private List<RejectedRecord> rejectedRecords = new ArrayList<>();

    private Instant importedAt;

    public static class RejectedRecord {
        private int index;          // record index in file (1-based)
        private String email;
        private String reason;

        public RejectedRecord() {}

        public RejectedRecord(int index, String email, String reason) {
            this.index = index;
            this.email = email;
            this.reason = reason;
        }

        public int getIndex() { return index; }
        public String getEmail() { return email; }
        public String getReason() { return reason; }

        public void setIndex(int index) { this.index = index; }
        public void setEmail(String email) { this.email = email; }
        public void setReason(String reason) { this.reason = reason; }
    }

    public int getTotalRecords() { return totalRecords; }
    public int getImported() { return imported; }
    public int getSkipped() { return skipped; }
    public int getRejected() { return rejected; }
    public List<String> getImportedPublicRefs() { return importedPublicRefs; }
    public List<String> getSkippedEmails() { return skippedEmails; }
    public List<RejectedRecord> getRejectedRecords() { return rejectedRecords; }
    public Instant getImportedAt() { return importedAt; }

    public void setTotalRecords(int totalRecords) { this.totalRecords = totalRecords; }
    public void setImported(int imported) { this.imported = imported; }
    public void setSkipped(int skipped) { this.skipped = skipped; }
    public void setRejected(int rejected) { this.rejected = rejected; }
    public void setImportedPublicRefs(List<String> importedPublicRefs) { this.importedPublicRefs = importedPublicRefs; }
    public void setSkippedEmails(List<String> skippedEmails) { this.skippedEmails = skippedEmails; }
    public void setRejectedRecords(List<RejectedRecord> rejectedRecords) { this.rejectedRecords = rejectedRecords; }
    public void setImportedAt(Instant importedAt) { this.importedAt = importedAt; }
}
