package org.shorty.utils;

import java.security.SecureRandom;

public class RandomString {
    private static final SecureRandom random = new SecureRandom();
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int DEFAULT_LENGTH = 32;
    private String alphabet;

    // Constructor
    public RandomString(String alphabet) {
        this.alphabet = (alphabet == null ? ALPHABET : alphabet);
    }

    // Generate random string with default length
    public String generate() {
        return this.generateRandomString(DEFAULT_LENGTH);
    }

    // Generate random string with defined length
    public String generate(int length) {
        return this.generateRandomString(length);
    }

    // Generator
    private String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(this.alphabet.length());
            char randomChar = this.alphabet.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }
}

