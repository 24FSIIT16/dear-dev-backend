package ch.fhnw.deardevbackend.services;

import ch.fhnw.deardevbackend.dto.SubmitHappinessSurveyDTO;
import ch.fhnw.deardevbackend.dto.SubmitWorkKindSurveyDTO;
import ch.fhnw.deardevbackend.entities.HappinessSurvey;
import ch.fhnw.deardevbackend.entities.WorkKindSurvey;
import ch.fhnw.deardevbackend.mapper.SubmitHappinessSurveyMapper;
import ch.fhnw.deardevbackend.mapper.SubmitWorkKindSurveyMapper;
import ch.fhnw.deardevbackend.repositories.HappinessSurveyRepository;
import ch.fhnw.deardevbackend.repositories.WorkKindSurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyService {

    @Autowired
    private HappinessSurveyRepository happinessSurveyRepository;

    @Autowired
    private WorkKindSurveyRepository workKindSurveyRepository;

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

}
