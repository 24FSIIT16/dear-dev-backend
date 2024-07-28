package ch.fhnw.deardevbackend.services;

import ch.fhnw.deardevbackend.controller.exceptions.YappiException;
import ch.fhnw.deardevbackend.dto.*;
import ch.fhnw.deardevbackend.entities.EmotionSurvey;
import ch.fhnw.deardevbackend.entities.HappinessSurvey;
import ch.fhnw.deardevbackend.entities.WorkKind;
import ch.fhnw.deardevbackend.entities.WorkKindSurvey;
import ch.fhnw.deardevbackend.mapper.DashboardMapper;
import ch.fhnw.deardevbackend.mapper.SubmitEmotionSurveyMapper;
import ch.fhnw.deardevbackend.mapper.SubmitHappinessSurveyMapper;
import ch.fhnw.deardevbackend.mapper.SubmitWorkKindSurveyMapper;
import ch.fhnw.deardevbackend.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private WorkKindRepository workKindRepository;

    @Autowired
    private SubmitHappinessSurveyMapper submitHappinessSurveyMapper;

    @Autowired
    private SubmitWorkKindSurveyMapper submitWorkKindSurveyMapper;

    @Autowired
    private SubmitEmotionSurveyMapper submitEmotionSurveyMapper;

    @Transactional
    public HappinessSurvey saveHappinessSurvey(SubmitHappinessSurveyDTO dto) {
        try {
            HappinessSurvey survey = submitHappinessSurveyMapper.toHappinessSurvey(dto);
            return happinessSurveyRepository.save(survey);
        } catch (DataIntegrityViolationException ex) {
            throw new YappiException("Error saving dappiness data");
        }
    }

    @Transactional
    public WorkKindSurvey saveWorkKindSurvey(SubmitWorkKindSurveyDTO dto) {
        try {
            WorkKindSurvey survey = submitWorkKindSurveyMapper.toWorkKindSurvey(dto);
            return workKindSurveyRepository.save(survey);
        } catch (DataIntegrityViolationException ex) {
            throw new YappiException("Error saving worktype data");
        }
    }

    @Transactional
    public EmotionSurvey saveEmotionSurvey(SubmitEmotionSurveyDTO dto) {
        try {
            EmotionSurvey survey = submitEmotionSurveyMapper.toEmotionSurvey(dto);
            return emotionSurveyRepository.save(survey);
        } catch (DataIntegrityViolationException ex) {
            throw new YappiException("Error saving emotion data");
        }
    }

    @Transactional(readOnly = true)
    public Integer getAverageScoreByUserId(Integer userId) {
        try {
            List<Object[]> dailyAverages = happinessSurveyRepository.findDailyAveragesByUserId(userId);

            if (dailyAverages.isEmpty()) {
                return null;
            }

            double total = 0;
            for (Object[] dailyAverage : dailyAverages) {
                total += (Double) dailyAverage[1];
            }

            double overallAverage = total / dailyAverages.size();
            return (int) Math.round(overallAverage);
        } catch (Exception ex) {
            throw new YappiException("Error calculating average score for user ID: " + userId);
        }
    }

    @Transactional(readOnly = true)
    public DashboardDTO getDashboardDataByUserId(Integer userId) {
        try {

            Integer averageScore = getAverageScoreByUserId(userId);

            List<Object[]> results = workKindSurveyRepository.findWorkKindCountByUserId(userId);

            if (results.isEmpty()) {
                return DashboardMapper.INSTANCE.toDashboardDTO(null, null, null, averageScore, null);
            }

            Object[] result = results.get(0);
            int workKindId = (int) result[0];
            long voteCount = (long) result[1];
            String workKindName = workKindRepository.findById(workKindId)
                    .map(WorkKind::getName)
                    .orElse("Unknown");

            Integer happinessScore = workKindSurveyRepository.findAverageHappinessScoreByWorkKindIdAndUserId(workKindId, userId)
                    .orElse(null);

            return DashboardMapper.INSTANCE.toDashboardDTO(workKindId, workKindName, (int) voteCount, averageScore, happinessScore);
        } catch (Exception ex) {
            throw new YappiException("Error fetching dashboard data for user ID: " + userId);
        }
    }

}
