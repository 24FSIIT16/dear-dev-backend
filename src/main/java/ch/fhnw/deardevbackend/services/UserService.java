package ch.fhnw.deardevbackend.services;

import ch.fhnw.deardevbackend.dto.UserAndProviderDTO;
import ch.fhnw.deardevbackend.dto.UserDTO;
import ch.fhnw.deardevbackend.entities.User;
import ch.fhnw.deardevbackend.mapper.UserMapper;
import ch.fhnw.deardevbackend.mapper.UserProviderMapper;
import ch.fhnw.deardevbackend.repositories.AccountRepository;
import ch.fhnw.deardevbackend.repositories.TeamMemberRepository;
import ch.fhnw.deardevbackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TeamMemberRepository teamMemberRepository;
    @Autowired
    private UserProviderMapper userProviderMapper;
    @Autowired
    private UserMapper userMapper;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public UserDTO getUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new OpenApiResourceNotFoundException("User not found"));
        String provider = accountRepository.findProviderByUserId(id);
        Boolean isInTeam = teamMemberRepository.userIsInTeam(id);
        return userMapper.toDto(user, provider, isInTeam);
    }

    public UserAndProviderDTO getUserWithProviderById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new OpenApiResourceNotFoundException("User not found"));
        String provider = accountRepository.findProviderByUserId(id);
        return userProviderMapper.toDto(user, provider);
    }

    @Transactional
    public void updateUser(int id, String username) {
        User user = userRepository.findById(id).orElseThrow(() -> new OpenApiResourceNotFoundException("User not found"));
        if (user != null) {
            user.setUsername(username);
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User not found");
        }
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
