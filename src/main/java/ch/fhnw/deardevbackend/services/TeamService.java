package ch.fhnw.deardevbackend.services;

import ch.fhnw.deardevbackend.controller.exceptions.YappiException;
import ch.fhnw.deardevbackend.dto.*;
import ch.fhnw.deardevbackend.entities.*;
import ch.fhnw.deardevbackend.mapper.CreateTeamMapper;
import ch.fhnw.deardevbackend.mapper.TeamAndRoleMapper;
import ch.fhnw.deardevbackend.mapper.TeamConfigMapper;
import ch.fhnw.deardevbackend.repositories.TeamConfigRepository;
import ch.fhnw.deardevbackend.repositories.TeamMemberRepository;
import ch.fhnw.deardevbackend.repositories.TeamRepository;
import ch.fhnw.deardevbackend.repositories.WorkKindRepository;
import ch.fhnw.deardevbackend.util.TeamCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamMemberRepository teamMemberRepository;
    @Autowired
    private TeamConfigRepository teamConfigRepository;
    @Autowired
    private WorkKindRepository workKindRepository;
    @Autowired
    private CreateTeamMapper createTeamMapper;
    @Autowired
    private TeamAndRoleMapper teamAndRoleMapper;
    @Autowired
    private TeamConfigMapper teamConfigMapper;

    public List<TeamAndRoleDTO> getTeamsAndRoleByUserId(Integer userId) {
        List<TeamMember> teamMembers = teamMemberRepository.findByUserId(userId);

        return teamMembers.stream()
                .map(member -> {
                    Team team = teamRepository.findById(member.getTeamId()).orElseThrow(() -> new YappiException("Team not found with id: " + member.getTeamId()));
                    return teamAndRoleMapper.toDTO(team, member.getRole());
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public Team createTeam(CreateTeamDTO teamDTO) {
        String uniqueCode = generateUniqueTeamCode();

        TeamConfig config = TeamConfig.builder()
                .workKindIds(Arrays.asList(1, 2))
                .happinessSurvey(true)
                .workKindSurvey(true)
                .emotionSurvey(true)
                .build();

        TeamConfig savedTeamConfig = teamConfigRepository.save(config);

        Team team = createTeamMapper.toTeam(teamDTO);
        team.setCode(uniqueCode);
        team.setConfigId(savedTeamConfig.getId());

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

    @Transactional(readOnly = true)
    public TeamConfigDTO getTeamConfigByTeamId(Integer teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new YappiException("Team not found with id: " + teamId));
        Integer configId = team.getConfigId();
        TeamConfig config = teamConfigRepository.findById(configId).orElseThrow(() -> new YappiException("Team config not found with id: " + configId));
        List<WorkKind> workKinds = workKindRepository.findAllById(config.getWorkKindIds());
        List<WorkKindDTO> workKindDTOs = workKinds.stream()
                .map(teamConfigMapper::toWorkKindDTO)
                .toList();

        return teamConfigMapper.toTeamConfigDTO(config, team, workKindDTOs);
    }

    private String generateUniqueTeamCode() {
        String code;
        do {
            code = TeamCodeGenerator.generateUniqueCode();
        } while (teamRepository.existsByCode(code));
        return code;
    }

    public List<Integer> getTeamIdsForUser(int userId) {
        return teamMemberRepository.findTeamIdByUserId(userId);
    }
}
