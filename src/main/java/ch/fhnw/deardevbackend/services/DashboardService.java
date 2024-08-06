package ch.fhnw.deardevbackend.services;

import ch.fhnw.deardevbackend.annotations.ValidateUserId;
import ch.fhnw.deardevbackend.controller.exceptions.YappiException;
import ch.fhnw.deardevbackend.dto.DashboardDTO;
import ch.fhnw.deardevbackend.dto.SubmitEmotionSurveyDTO;
import ch.fhnw.deardevbackend.dto.SubmitHappinessSurveyDTO;
import ch.fhnw.deardevbackend.dto.SubmitWorkKindSurveyDTO;
import ch.fhnw.deardevbackend.entities.EmotionSurvey;
import ch.fhnw.deardevbackend.entities.HappinessSurvey;
import ch.fhnw.deardevbackend.entities.WorkKindSurvey;
import ch.fhnw.deardevbackend.mapper.SubmitEmotionSurveyMapper;
import ch.fhnw.deardevbackend.mapper.SubmitHappinessSurveyMapper;
import ch.fhnw.deardevbackend.mapper.SubmitWorkKindSurveyMapper;
import ch.fhnw.deardevbackend.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DashboardService {

    @Autowired
    private HappinessSurveyRepository happinessSurveyRepository;

    @Autowired
    private WorkKindSurveyRepository workKindSurveyRepository;

    @Autowired
    private EmotionSurveyRepository emotionSurveyRepository;

    @Autowired
    private SubmitHappinessSurveyMapper submitHappinessSurveyMapper;

    @Autowired
    private SubmitWorkKindSurveyMapper submitWorkKindSurveyMapper;

    @Autowired
    private SubmitEmotionSurveyMapper submitEmotionSurveyMapper;

    @Autowired
    private SprintConfigRepository sprintConfigRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @ValidateUserId
    @Transactional
    public HappinessSurvey saveHappinessSurvey(SubmitHappinessSurveyDTO dto) {
        try {
            HappinessSurvey survey = submitHappinessSurveyMapper.toHappinessSurvey(dto);
            return happinessSurveyRepository.save(survey);
        } catch (DataIntegrityViolationException ex) {
            throw new YappiException("Error saving dappiness data");
        }
    }

    @ValidateUserId
    @Transactional
    public WorkKindSurvey saveWorkKindSurvey(SubmitWorkKindSurveyDTO dto) {
        try {
            WorkKindSurvey survey = submitWorkKindSurveyMapper.toWorkKindSurvey(dto);
            return workKindSurveyRepository.save(survey);
        } catch (DataIntegrityViolationException ex) {
            throw new YappiException("Error saving worktype data");
        }
    }

    @ValidateUserId
    @Transactional
    public EmotionSurvey saveEmotionSurvey(SubmitEmotionSurveyDTO dto) {
        try {
            EmotionSurvey survey = submitEmotionSurveyMapper.toEmotionSurvey(dto);
            return emotionSurveyRepository.save(survey);
        } catch (DataIntegrityViolationException ex) {
            throw new YappiException("Error saving emotion data");
        }
    }

    public DashboardDTO getDashboardData(Integer userId) {
        try {
            DashboardDTO dto = new DashboardDTO();

            dto.setLastSubmissionDateOfHappiness(
                    Optional.ofNullable(happinessSurveyRepository.findLastSubmissionDateByUserId(userId))
                            .map(LocalDateTime::toLocalDate)
                            .orElse(null)
            );

            dto.setActiveSprintEndDate(
                    sprintConfigRepository.findClosestActiveSprintEndDateByUserId(userId)
                            .orElse(null)
            );

            dto.setMostTrackedEmotions(
                    emotionSurveyRepository.findTopTwoTrackedEmotions(userId)
            );

            dto.setMostTrackedWorkKind(
                    workKindSurveyRepository.findMostTrackedWorkType(userId)
            );

            dto.setNumberOfDaysWithHappinessSurvey(
                    happinessSurveyRepository.countDaysWithHappinessSurveyThisYear(userId)
            );

            dto.setNumberOfHappinessSurveysToday(
                    happinessSurveyRepository.numberOfHappinessSurveysToday(userId)
            );

            dto.setNumberOfTeams(
                    teamMemberRepository.countTeamsUserIsIn(userId)
            );

            dto.setNumberOfTeamMembers(
                    teamMemberRepository.countTotalTeamMembersForUser(userId)
            );

            dto.setAverageHappinessScore(
                    Optional.ofNullable(happinessSurveyRepository.findAverageHappinessScore(userId))
                            .map(score -> Math.round(score * 10.0) / 10.0)
                            .orElse(null)
            );

            return dto;
        } catch (Exception ex) {
            throw new YappiException("Error loading dashboard data for user ID: " + userId);
        }
    }

}
