package leetcode;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidNumberTest {
    private final String[] validNumbers = new String[]{
            "2", "0089", "-0.1", "+3.14", "4.", "-.9", "2e10",
            "-90E3", "3e+7", "+6e-1", "53.5e93", "-123.456e789", "0", "46.e3", ".0e7"
    };
    private final String[] notValidNumbers = new String[]{
            "abc", "1a", "1e", "e3", "99e2.5", "--6", "-+3", "95a54e53", ".", "..", ".e1", "4e+", "+.", "+E3", "1e."
    };

    private final ValidNumber sut = new ValidNumber();

    @Test
    void isNumberValid() {
        for (String validNumber : validNumbers) {
            assertTrue(sut.isNumber(validNumber));
        }
    }

    @Test
    void isNumberNotValid() {
        for (String njtValidNumber : notValidNumbers) {
            assertFalse(sut.isNumber(njtValidNumber));
        }
    }
}