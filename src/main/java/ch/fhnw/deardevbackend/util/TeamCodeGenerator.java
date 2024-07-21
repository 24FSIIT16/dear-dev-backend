package ch.fhnw.deardevbackend.util;

import java.security.SecureRandom;
import java.util.Random;

public class TeamCodeGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Integer CODE_LENGTH = 4;
    private static final Random RANDOM = new SecureRandom();

    public static String generateUniqueCode() {
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return code.toString();
    }

}
