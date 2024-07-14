package ch.fhnw.deardevbackend.services;

import ch.fhnw.deardevbackend.controller.exceptions.YappiException;
import ch.fhnw.deardevbackend.dto.CreateTeamDTO;
import ch.fhnw.deardevbackend.dto.JoinTeamDTO;
import ch.fhnw.deardevbackend.entities.Role;
import ch.fhnw.deardevbackend.entities.Team;
import ch.fhnw.deardevbackend.entities.TeamMember;
import ch.fhnw.deardevbackend.mapper.CreateTeamMapper;
import ch.fhnw.deardevbackend.repositories.TeamMemberRepository;
import ch.fhnw.deardevbackend.repositories.TeamRepository;
import ch.fhnw.deardevbackend.util.TeamCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamMemberRepository teamMemberRepository;
    @Autowired
    private CreateTeamMapper createTeamMapper;

    @Transactional
    public Team createTeam(CreateTeamDTO teamDTO) {
        String uniqueCode = generateUniqueTeamCode();

        Team team = createTeamMapper.toTeam(teamDTO);
        team.setCode(uniqueCode);

        Team savedTeam = teamRepository.save(team);

        TeamMember teamMember = TeamMember.builder()
                .userId(teamDTO.getUserId())
                .teamId(savedTeam.getId())
                .role(Role.ADMIN)
                .active(true)
                .build();

        teamMemberRepository.save(teamMember);
        return savedTeam;
    }

    @Transactional
    public Team joinTeam(JoinTeamDTO joinTeamDTO) {
        Team team = teamRepository.findByCode(joinTeamDTO.getCode()).orElseThrow(() -> new YappiException("Team not found with code: " + joinTeamDTO.getCode()));

        try {
            TeamMember teamMember = TeamMember.builder()
                    .userId(joinTeamDTO.getUserId())
                    .teamId(team.getId())
                    .role(Role.MEMBER)
                    .active(true)
                    .build();

            teamMemberRepository.save(teamMember);
        } catch (DataIntegrityViolationException ex) {
            throw new YappiException("User with id: " + joinTeamDTO.getUserId() + " is already a member of the team with id: " + team.getId());
        }
        return team;
    }

    private String generateUniqueTeamCode() {
        String code;
        do {
            code = TeamCodeGenerator.generateUniqueCode();
        } while (teamRepository.existsByCode(code));
        return code;
    }
}
