package ch.fhnw.deardevbackend.services;

import ch.fhnw.deardevbackend.dto.CreateTeamDTO;
import ch.fhnw.deardevbackend.entities.Role;
import ch.fhnw.deardevbackend.entities.Team;
import ch.fhnw.deardevbackend.entities.TeamMember;
import ch.fhnw.deardevbackend.mapper.CreateTeamMapper;
import ch.fhnw.deardevbackend.repositories.TeamMemberRepository;
import ch.fhnw.deardevbackend.repositories.TeamRepository;
import ch.fhnw.deardevbackend.util.TeamCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    private String generateUniqueTeamCode() {
        String code;
        do {
            code = TeamCodeGenerator.generateUniqueCode();
        } while (teamRepository.existsByCode(code));
        return code;
    }
}
