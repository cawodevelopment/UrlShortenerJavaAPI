package com.io.github.cawodevelopment.urlshortener;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RandomStringUtilityTest {

    @Test
    void generateCodeReturnsFiveAlphanumericCharacters() {
        String code = RandomStringUtility.generateCode();

        assertEquals(5, code.length());
        assertTrue(code.matches("[A-Za-z0-9]{5}"));
    }
}
