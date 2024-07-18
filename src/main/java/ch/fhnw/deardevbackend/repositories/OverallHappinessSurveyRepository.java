package ch.fhnw.deardevbackend.repositories;

import ch.fhnw.deardevbackend.entities.OverallHappinessSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OverallHappinessSurveyRepository extends JpaRepository<OverallHappinessSurvey, Integer> {

    @Query("SELECT o FROM OverallHappinessSurvey o WHERE o.userId = :userId")
    List<OverallHappinessSurvey> findByUserId(@Param("userId") Integer userId);

    @Query("SELECT AVG(o.score) FROM OverallHappinessSurvey o WHERE o.userId = :userId")
    Double findAverageScoreByUserId(@Param("userId") Integer userId);

}
