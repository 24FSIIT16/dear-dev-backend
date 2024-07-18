package ch.fhnw.deardevbackend.services;

import ch.fhnw.deardevbackend.entities.OverallHappinessSurvey;
import ch.fhnw.deardevbackend.repositories.OverallHappinessSurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OverallHappinessSurveyService {

    @Autowired
    private OverallHappinessSurveyRepository repository;

    public OverallHappinessSurvey save(OverallHappinessSurvey survey) {
        return repository.save(survey);
    }

    public List<OverallHappinessSurvey> getAllByUserId(Integer userId) {
        return repository.findByUserId(userId);
    }

    public Double getAverageScoreByUserId(Integer userId) {
        return repository.findAverageScoreByUserId(userId);
    }
}
