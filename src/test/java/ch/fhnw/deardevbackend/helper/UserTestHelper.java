package ch.fhnw.deardevbackend.helper;

import ch.fhnw.deardevbackend.entities.User;

import java.util.List;

public class UserTestHelper {
    public static List<User> getMockUsers() {
        var user1 = User.builder()
                .id(1)
                .name("Hans")
                .email("hans@hans.com")
                .build();

        var user2 = User.builder()
                .id(2)
                .name("Eve")
                .email("eve@eve.com")
                .build();

        return List.of(user1, user2);
    }
}
