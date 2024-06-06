package com.threec.auth.utils;

import io.jsonwebtoken.Claims;
import lombok.Data;

@Data
public class JWTValidationResult {
    private boolean isValid;
    private String errorMessage;
    private Claims claims;

    public JWTValidationResult(boolean isValid, String errorMessage, Claims claims) {
        this.isValid = isValid;
        this.errorMessage = errorMessage;
        this.claims = claims;
    }

    // Getters
    public boolean isValid() {
        return isValid;
    }

    // Static factory method for success
    public static JWTValidationResult success(Claims claims) {
        return new JWTValidationResult(true, null, claims);
    }

    // Static factory method for failure
    public static JWTValidationResult failure(String errorMessage) {
        return new JWTValidationResult(false, errorMessage, null);
    }
}
