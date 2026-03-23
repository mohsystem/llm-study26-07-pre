package com.um.springbootprojstructure.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
@EnableConfigurationProperties(LdapProperties.class)
public class LdapConfig {

    @Bean
    public LdapContextSource ldapContextSource(LdapProperties props) {
        LdapContextSource source = new LdapContextSource();
        source.setUrl(props.getUrls());
        source.setBase(props.getBaseDn());
        source.setUserDn(props.getBindDn());
        source.setPassword(props.getBindPassword());
        source.afterPropertiesSet();
        return source;
    }

    @Bean
    public LdapTemplate ldapTemplate(LdapContextSource contextSource) {
        return new LdapTemplate(contextSource);
    }
}
