package ch.fhnw.deardevbackend.services;

import ch.fhnw.deardevbackend.entities.User;
import ch.fhnw.deardevbackend.helper.UserTestHelper;
import ch.fhnw.deardevbackend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private List<User> users;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
        users = UserTestHelper.getMockUsers();
    }

    @Test
    void getAllUsersTest() {
        when(userRepository.findAll()).thenReturn(users);

        List<User> actualUsers = userService.getAllUsers();

        assertThat(actualUsers).isEqualTo(users);
    }

    @Test
    void getUserByIdTest() {
        User user = users.getFirst();
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        User actualUser = userService.getUserById(user.getId());

        assertThat(actualUser).isEqualTo(user);
    }

    @Test
    void findUserByEmailTest() {
        User user = users.getFirst();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        Optional<User> actualUser = userService.findUserByEmail(user.getEmail());

        assertThat(actualUser).isEqualTo(Optional.of(user));
    }

    @Test
    void findUserByIdTest() {
        User user = users.getFirst();
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        Optional<User> actualUser = userService.findUserById(user.getId());

        assertThat(actualUser).isEqualTo(Optional.of(user));
    }
}
