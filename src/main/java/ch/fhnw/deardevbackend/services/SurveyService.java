package ch.fhnw.deardevbackend.services;

import ch.fhnw.deardevbackend.entities.HappinessSurvey;
import ch.fhnw.deardevbackend.repositories.HappinessSurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyService {

    @Autowired
    private HappinessSurveyRepository repository;

    public HappinessSurvey save(HappinessSurvey survey) {
        return repository.save(survey);
    }

    public List<HappinessSurvey> getAllByUserId(Integer userId) {
        return repository.findByUserId(userId);
    }

    public Double getAverageScoreByUserId(Integer userId) {
        return repository.findAverageScoreByUserId(userId);
    }
}
