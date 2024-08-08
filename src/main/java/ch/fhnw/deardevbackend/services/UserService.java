package ch.fhnw.deardevbackend.services;

import ch.fhnw.deardevbackend.controller.exceptions.YappiException;
import ch.fhnw.deardevbackend.dto.UserAndProviderDTO;
import ch.fhnw.deardevbackend.dto.UserDTO;
import ch.fhnw.deardevbackend.entities.Role;
import ch.fhnw.deardevbackend.entities.Team;
import ch.fhnw.deardevbackend.entities.TeamMember;
import ch.fhnw.deardevbackend.entities.User;
import ch.fhnw.deardevbackend.mapper.UserMapper;
import ch.fhnw.deardevbackend.mapper.UserProviderMapper;
import ch.fhnw.deardevbackend.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Comparator;
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
    private TeamRepository teamRepository;
    @Autowired
    private UserProviderMapper userProviderMapper;
    @Autowired
    private EmotionSurveyRepository emotionSurveyRepository;
    @Autowired
    private HappinessSurveyRepository happinessSurveyRepository;
    @Autowired
    private WorkKindSurveyRepository workKindSurveyRepository;
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
    public void updateUser(Integer id, String username, String githubUserName) {
        User user = userRepository.findById(id).orElseThrow(() -> new OpenApiResourceNotFoundException("User not found"));
        if (user != null) {
            user.setUsername(username);
            user.setGithubUserName(githubUserName);
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    @Transactional
    public void deleteUser(Integer userId) {
        emotionSurveyRepository.deleteByUserId(userId);
        happinessSurveyRepository.deleteByUserId(userId);
        workKindSurveyRepository.deleteByUserId(userId);
        List<TeamMember> teamMembers = teamMemberRepository.findByUserId(userId);
        for (TeamMember teamMember : teamMembers) {
            teamMember.setActive(false);
            if (teamMember.getRole().equals(Role.ADMIN)) {
                List<TeamMember> currentTeamMembers = teamMemberRepository.findByTeamId(teamMember.getTeamId());
                if (currentTeamMembers.size() > 1) {
                    TeamMember newAdmin = findClosestJoinedMember(teamMember, currentTeamMembers);
                    newAdmin.setRole(Role.ADMIN);
                    teamMemberRepository.save(newAdmin);
                } else {
                    Team team = teamRepository.findById(teamMember.getTeamId()).orElseThrow(() -> new YappiException("Team with id " + teamMember.getTeamId() + " not found"));
                    team.setActive(false);
                    teamRepository.save(team);
                }
            }
        }

        accountRepository.deleteByUserId(userId);
        userRepository.deleteById(userId);
    }
    // used for JWT filter
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // used for JWT filter
    public Optional<User> findUserById(Integer id) {
        return userRepository.findById(id);
    }

    private TeamMember findClosestJoinedMember(TeamMember currentAdmin, List<TeamMember> currentTeamMembers) {
        return currentTeamMembers.stream()
                .filter(member -> !member.equals(currentAdmin))
                .min(Comparator.comparing(member -> Math.abs(Duration.between(currentAdmin.getJoinedAt(), member.getJoinedAt()).toMillis())))
                .orElseThrow(() -> new YappiException("No closest joined member found"));
    }
}
