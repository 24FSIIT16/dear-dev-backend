package ch.fhnw.deardevbackend.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TeamCodeGeneratorTest {

    @Test
    void generateUniqueCode_returnsCodeOfCorrectLength() {
        String code = TeamCodeGenerator.generateUniqueCode();
        Assertions.assertEquals(4, code.length());
    }

    @Test
    void generateUniqueCode_returnsAlphanumericCode() {
        String code = TeamCodeGenerator.generateUniqueCode();
        Assertions.assertTrue(code.matches("[A-Za-z0-9]+"));
    }

    @Test
    void generateUniqueCode_generatesDifferentCodesOnSubsequentCalls() {
        String code1 = TeamCodeGenerator.generateUniqueCode();
        String code2 = TeamCodeGenerator.generateUniqueCode();
        Assertions.assertNotEquals(code1, code2);
    }
}