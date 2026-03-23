package com.um.springbootprojstructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.ldap")
public class LdapProperties {
    private boolean enabled;
    private String urls;
    private String baseDn;
    private String bindDn;
    private String bindPassword;

    private String userSearchBase = "";
    private String userSearchFilter = "(uid={0})";

    public boolean isEnabled() { return enabled; }
    public String getUrls() { return urls; }
    public String getBaseDn() { return baseDn; }
    public String getBindDn() { return bindDn; }
    public String getBindPassword() { return bindPassword; }
    public String getUserSearchBase() { return userSearchBase; }
    public String getUserSearchFilter() { return userSearchFilter; }

    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public void setUrls(String urls) { this.urls = urls; }
    public void setBaseDn(String baseDn) { this.baseDn = baseDn; }
    public void setBindDn(String bindDn) { this.bindDn = bindDn; }
    public void setBindPassword(String bindPassword) { this.bindPassword = bindPassword; }
    public void setUserSearchBase(String userSearchBase) { this.userSearchBase = userSearchBase; }
    public void setUserSearchFilter(String userSearchFilter) { this.userSearchFilter = userSearchFilter; }
}
