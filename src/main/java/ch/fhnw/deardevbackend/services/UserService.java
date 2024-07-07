package ch.fhnw.deardevbackend.services;

import ch.fhnw.deardevbackend.entities.User;
import ch.fhnw.deardevbackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    // used for JWT filter
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // used for JWT filter
    public Optional<User> findUserById(Integer id) {
        return userRepository.findById(id);
    }
}
