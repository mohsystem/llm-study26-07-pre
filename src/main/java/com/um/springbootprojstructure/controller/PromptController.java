package com.um.springbootprojstructure.controller;

import com.um.springbootprojstructure.config.PromptLoggingConfig;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prompts")
@Validated
public class PromptController {

    public static class PromptRequest {
        @NotBlank
        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    @PostMapping
    public void logPrompt(@Valid @RequestBody PromptRequest request) {
        PromptLoggingConfig.logText(request.getText());
    }
}
