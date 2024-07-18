package ch.fhnw.deardevbackend.repositories;

import ch.fhnw.deardevbackend.entities.OverallHappinessSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OverallHappinessSurveyRepository extends JpaRepository<OverallHappinessSurvey, Integer> {
}
