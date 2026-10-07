package com.io.github.cawodevelopment.urlshortener;

import org.springframework.context.annotation.Bean;

import java.security.SecureRandom;

public class RandomStringUtility {

    private static final String CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private static final SecureRandom RANDOM = new SecureRandom();

    @Bean
    public static String generateCode() {
        StringBuilder result = new StringBuilder(5);

        for (int i = 0; i < 5; i++) {
            result.append(CHARACTERS.charAt(
                    RANDOM.nextInt(CHARACTERS.length())
            ));
        }

        return result.toString();
    }
}
