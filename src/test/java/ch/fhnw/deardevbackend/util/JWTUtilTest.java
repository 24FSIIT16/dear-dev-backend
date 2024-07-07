package ch.fhnw.deardevbackend.util;

import ch.fhnw.deardevbackend.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JWTUtilTest {

    @Autowired
    private JWTUtil jwtUtil;

    private User user;

    @BeforeEach
    void setUp() {
        // Initialize JWTUtil with a secret for testing
        ReflectionTestUtils.setField(jwtUtil, "secret", "testSecret");

        // Setup a test user
        user = new User();
        user.setId(1);
        user.setEmail("test@example.com");
    }

    @Test
    void generateTokenTest() {
        String token = jwtUtil.generateToken(user);
        assertThat(token).isNotNull();
        assertThat(jwtUtil.extractEmail(token)).isEqualTo(user.getEmail());
        assertThat(jwtUtil.extractUserId(token)).isEqualTo(user.getId());
    }

    @Test
    void extractEmailTest() {
        String token = jwtUtil.generateToken(user);
        assertThat(jwtUtil.extractEmail(token)).isEqualTo(user.getEmail());
    }

    @Test
    void extractUserIdTest() {
        String token = jwtUtil.generateToken(user);
        assertThat(jwtUtil.extractUserId(token)).isEqualTo(user.getId());
    }

    @Test
    void validateToken_withValidToken() {
        String token = jwtUtil.generateToken(user);
        Assertions.assertTrue(jwtUtil.validateToken(token, user));
    }
}
