package com.um.springbootprojstructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.bootstrap.admin")
public class AdminBootstrapProperties {

    /**
     * Enable/disable creating the admin user at startup.
     */
    private boolean enabled = true;

    /**
     * Admin user full name
     */
    private String fullName = "System Administrator";

    /**
     * Admin user email (must be unique)
     */
    private String email = "admin@example.com";

    public boolean isEnabled() {
        return enabled;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
