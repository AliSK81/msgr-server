package com.msgrserver.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class TokenGeneratorUtil implements TokenGenerator {

    private final SecureRandom Random = new SecureRandom();
    private final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    public String generateNewToken() {
        byte[] randomBytes = new byte[24];
        Random.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
