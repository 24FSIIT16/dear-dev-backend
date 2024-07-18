package ch.fhnw.deardevbackend.repositories;

import ch.fhnw.deardevbackend.entities.WorkKindHappinessSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WorkKindHappinessSurveyRepository extends JpaRepository<WorkKindHappinessSurvey, Integer> {
}
