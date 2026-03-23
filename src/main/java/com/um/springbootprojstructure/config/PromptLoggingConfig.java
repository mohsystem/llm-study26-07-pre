package com.um.springbootprojstructure.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;

@Configuration
public class PromptLoggingConfig {

    private static final Path LOG_PATH = Path.of("user-prompt.log");

    @Bean
    public Filter promptLoggingFilter() {
        return new Filter() {
            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                    throws IOException, ServletException {
                if (request instanceof HttpServletRequest http) {
                    String line = String.format(
                            "%s | %s %s%s | ip=%s | ua=%s%n",
                            Instant.now(),
                            http.getMethod(),
                            http.getRequestURI(),
                            http.getQueryString() == null ? "" : "?" + http.getQueryString(),
                            request.getRemoteAddr(),
                            http.getHeader("User-Agent")
                    );
                    appendLine(line);
                }
                chain.doFilter(request, response);
            }
        };
    }

    private static synchronized void appendLine(String line) throws IOException {
        try (FileWriter fw = new FileWriter(LOG_PATH.toFile(), true)) {
            fw.write(line);
        }
    }

    public static void logText(String text) {
        String line = String.format("%s | USER_PROMPT | %s%n", Instant.now(), text);
        try {
            appendLine(line);
        } catch (IOException ignored) {
            // Do not break request processing if logging fails.
        }
    }
}
