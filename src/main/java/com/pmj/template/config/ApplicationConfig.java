package com.pmj.template.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pmj.template.security.UserPrincipal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class ApplicationConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return Optional.of("system");
            }
            return Optional.of(authentication.getName());
        };
    }

    // Custom user security evaluator for PreAuthorize annotations
    @Bean
    public UserSecurity userSecurity() {
        return new UserSecurity();
    }

    // This class will be used in @PreAuthorize annotations to check user permissions
    public static class UserSecurity {
        public boolean hasUserId(Authentication authentication, Long userId) {
            if (authentication == null || !authentication.isAuthenticated()) {
                return false;
            }

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            return userPrincipal.getId().equals(userId);
        }

        public boolean hasUsername(Authentication authentication, String username) {
            if (authentication == null || !authentication.isAuthenticated()) {
                return false;
            }

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            return userPrincipal.getUsername().equals(username);
        }
    }
}