package com.um.springbootprojstructure.config;

import com.um.springbootprojstructure.entity.Role;
import com.um.springbootprojstructure.entity.User;
import com.um.springbootprojstructure.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AdminBootstrapProperties.class)
public class AdminBootstrapConfig {

    @Bean
    ApplicationRunner adminBootstrapRunner(UserRepository userRepository,
                                           AdminBootstrapProperties props) {
        return args -> {
            if (!props.isEnabled()) {
                return;
            }

            // create only if missing
            if (!userRepository.existsByEmail(props.getEmail())) {
                User admin = new User(props.getFullName(), props.getEmail(), Role.ADMIN, true);
                userRepository.save(admin);
            }
        };
    }
}
