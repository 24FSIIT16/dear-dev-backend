package ch.fhnw.deardevbackend.services;

import ch.fhnw.deardevbackend.dto.TeamWithMembersDTO;
import ch.fhnw.deardevbackend.entities.Role;
import ch.fhnw.deardevbackend.entities.Team;
import ch.fhnw.deardevbackend.entities.TeamMemberWithUser;
import ch.fhnw.deardevbackend.mapper.TeamWithMembersMapper;
import ch.fhnw.deardevbackend.repositories.TeamMemberWithUserRepository;
import ch.fhnw.deardevbackend.repositories.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamMemberService {

    @Autowired
    private TeamMemberWithUserRepository teamMemberWithUserRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamWithMembersMapper teamWithMembersMapper;

    public TeamWithMembersDTO getTeamMembersByTeamId(Integer teamId, Integer userId) {
        List<TeamMemberWithUser> members = teamMemberWithUserRepository.findByTeamId(teamId);
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new RuntimeException("Team not found with id: " + teamId));

        boolean isAdmin = members.stream()
                .anyMatch(member -> member.getUser().getId().equals(userId) && member.getRole().equals(Role.ADMIN));

        return teamWithMembersMapper.toDTO(team, members, isAdmin);
    }
}
