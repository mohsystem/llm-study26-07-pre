package com.um.springbootprojstructure.dto;

import java.util.HashMap;
import java.util.Map;

public class ValidateDirectoryResponse {

    private boolean matched;
    private int matchCount;

    /**
     * A normalized view of the matched directory record (first match).
     */
    private Map<String, Object> record = new HashMap<>();

    public boolean isMatched() { return matched; }
    public int getMatchCount() { return matchCount; }
    public Map<String, Object> getRecord() { return record; }

    public void setMatched(boolean matched) { this.matched = matched; }
    public void setMatchCount(int matchCount) { this.matchCount = matchCount; }
    public void setRecord(Map<String, Object> record) { this.record = record; }
}
