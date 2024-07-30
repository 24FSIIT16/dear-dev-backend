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

import java.util.ArrayList;
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

        Team team = createTeamMapper.toTeam(teamDTO);
        team.setCode(uniqueCode);
        Team savedTeam = teamRepository.save(team);

        List<WorkKind> defaultWorkKinds = createDefaultWorkKinds(savedTeam.getId());
        List<Integer> defaultWorkKindIds = defaultWorkKinds.stream().map(WorkKind::getId).toList();
        TeamConfig config = TeamConfig.builder()
                .workKindIds(defaultWorkKindIds)
                .happinessSurvey(true)
                .workKindSurvey(true)
                .emotionSurvey(true)
                .build();

        TeamConfig savedTeamConfig = teamConfigRepository.save(config);
        savedTeam.setConfigId(savedTeamConfig.getId());
        teamRepository.save(savedTeam);

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

    @Transactional
    public TeamConfigDTO updateTeamConfig(Integer teamId, TeamConfigDTO updateDTO) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new YappiException("Team not found with id: " + teamId));
        team.setName(updateDTO.getTeamName());

        TeamConfig teamConfig = teamConfigRepository.findById(team.getConfigId()).orElseThrow(() -> new YappiException("Team config not found with id: " + team.getConfigId()));
        teamConfig.setHappinessSurvey(updateDTO.getHappinessSurvey());
        teamConfig.setWorkKindSurvey(updateDTO.getWorkKindSurvey());
        teamConfig.setEmotionSurvey(updateDTO.getEmotionSurvey());

        List<WorkKind> updatedWorkKinds = updateDTO.getWorkKinds().stream()
                .map(workKindDTO -> {
                    WorkKind workKind;
                    if (workKindDTO.getId() != null) {
                        workKind = workKindRepository.findById(workKindDTO.getId()).orElseGet(() -> workKindRepository.save(teamConfigMapper.toWorkKind(workKindDTO)));
                        if (workKind.getId().equals(workKindDTO.getId())) {
                            workKind.setName(workKindDTO.getName());
                            workKindRepository.save(workKind);
                        }
                    } else {
                        workKind = workKindRepository.findByNameAndTeamId(workKindDTO.getName(), teamId)
                                .orElseGet(() -> {
                                    WorkKind newWorkKind = new WorkKind();
                                    newWorkKind.setName(workKindDTO.getName());
                                    newWorkKind.setTeamId(teamId);
                                    return workKindRepository.save(newWorkKind);
                                });
                    }
                    return workKind;
                })
                .toList();

        List<Integer> updatedWorkKindIds = updatedWorkKinds.stream().map(WorkKind::getId).toList();
        List<Integer> newWorkKindIds = new ArrayList<>(updatedWorkKindIds);
        teamConfig.setWorkKindIds(newWorkKindIds);
        teamConfig.setWorkKinds(updatedWorkKinds);

        teamRepository.save(team);
        teamConfigRepository.save(teamConfig);

        List<WorkKindDTO> workKindDTOs = updatedWorkKinds.stream().map(teamConfigMapper::toWorkKindDTO).toList();

        return teamConfigMapper.toTeamConfigDTO(teamConfig, team, workKindDTOs);
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

    private List<WorkKind> createDefaultWorkKinds(Integer teamId) {
        List<WorkKind> defaultWorkKinds = Arrays.asList(
                new WorkKind(null, "Coding", teamId),
                new WorkKind(null, "Meeting", teamId),
                new WorkKind(null, "Scrum Events", teamId)
        );
        return workKindRepository.saveAll(defaultWorkKinds);
    }
}
