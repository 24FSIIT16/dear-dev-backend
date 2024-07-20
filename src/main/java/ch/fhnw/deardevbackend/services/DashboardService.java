package ch.fhnw.deardevbackend.services;

import ch.fhnw.deardevbackend.dto.DashboardDTO;
import ch.fhnw.deardevbackend.dto.SubmitHappinessSurveyDTO;
import ch.fhnw.deardevbackend.dto.SubmitWorkKindSurveyDTO;
import ch.fhnw.deardevbackend.entities.HappinessSurvey;
import ch.fhnw.deardevbackend.entities.WorkKind;
import ch.fhnw.deardevbackend.entities.WorkKindSurvey;
import ch.fhnw.deardevbackend.mapper.DashboardMapper;
import ch.fhnw.deardevbackend.mapper.SubmitHappinessSurveyMapper;
import ch.fhnw.deardevbackend.mapper.SubmitWorkKindSurveyMapper;
import ch.fhnw.deardevbackend.repositories.HappinessSurveyRepository;
import ch.fhnw.deardevbackend.repositories.WorkKindRepository;
import ch.fhnw.deardevbackend.repositories.WorkKindSurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    private WorkKindRepository workKindRepository;

    @Autowired
    private SubmitHappinessSurveyMapper submitHappinessSurveyMapper;

    @Autowired
    private SubmitWorkKindSurveyMapper submitWorkKindSurveyMapper;

    @Transactional
    public HappinessSurvey save(SubmitHappinessSurveyDTO dto) {
        HappinessSurvey survey = submitHappinessSurveyMapper.toHappinessSurvey(dto);
        return happinessSurveyRepository.save(survey);
    }

    @Transactional
    public WorkKindSurvey save(SubmitWorkKindSurveyDTO dto) {
        WorkKindSurvey survey = submitWorkKindSurveyMapper.toWorkKindSurvey(dto);
        return workKindSurveyRepository.save(survey);
    }

    @Transactional(readOnly = true)
    public Integer getAverageScoreByUserId(int userId) {
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
    }

    @Transactional(readOnly = true)
    public DashboardDTO getDashboardDataByUserId(int userId) {
        List<Object[]> results = workKindSurveyRepository.findMostVotedWorkKindByUserId(userId);
        if (results.isEmpty()) {
            return null;
        }
        Object[] result = results.get(0);
        Integer workKindId = (Integer) result[0];
        Long voteCount = (Long) result[1];
        String workKindName = workKindRepository.findById(workKindId)
                .map(WorkKind::getName)
                .orElse("Unknown");

        Integer averageScore = getAverageScoreByUserId(userId);


        return DashboardMapper.INSTANCE.toDashboardDTO(workKindId, workKindName, voteCount, averageScore);
    }

}
