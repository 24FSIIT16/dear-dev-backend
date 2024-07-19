package ch.fhnw.deardevbackend.services;

import ch.fhnw.deardevbackend.dto.SubmitHappinessSurveyDTO;
import ch.fhnw.deardevbackend.entities.HappinessSurvey;
import ch.fhnw.deardevbackend.mapper.SubmitHappinessSurveyMapper;
import ch.fhnw.deardevbackend.repositories.HappinessSurveyRepository;
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
    private SubmitHappinessSurveyMapper submitHappinessSurveyMapper;

    @Transactional
    public HappinessSurvey save(SubmitHappinessSurveyDTO dto) {
        HappinessSurvey survey = submitHappinessSurveyMapper.toHappinessSurvey(dto);
        return happinessSurveyRepository.save(survey);
    }


    public List<HappinessSurvey> getAllByUserId(Integer userId) {
        return happinessSurveyRepository.findByUserId(userId);
    }

    public Double getAverageScoreByUserId(Integer userId) {
        return happinessSurveyRepository.findAverageScoreByUserId(userId);
    }
}
