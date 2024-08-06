package ch.fhnw.deardevbackend.repositories;

import ch.fhnw.deardevbackend.entities.HappinessSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface HappinessSurveyRepository extends JpaRepository<HappinessSurvey, Integer> {

    @Query("SELECT CAST(s.submitted AS date) AS day, AVG(s.score) AS dailyAverage FROM HappinessSurvey s WHERE s.userId = :userId GROUP BY day")
    List<Object[]> findDailyAveragesByUserId(Integer userId);

    @Query("SELECT MAX(h.submitted) FROM HappinessSurvey h WHERE h.userId = :userId")
    LocalDateTime findLastSubmissionDateByUserId(Integer userId);

    @Query("SELECT COUNT(DISTINCT(CAST(h.submitted AS date))) FROM HappinessSurvey h WHERE h.userId = :userId")
    int countDaysWithHappinessSurveyThisYear(@Param("userId") Integer userId);

    @Query("SELECT COUNT(h) FROM HappinessSurvey h WHERE h.userId = :userId AND CAST(h.submitted AS date) = CURRENT_DATE")
    int numberOfHappinessSurveysToday(@Param("userId") Integer userId);

    @Query("SELECT AVG(h.score) FROM HappinessSurvey h WHERE h.userId = :userId")
    Double findAverageHappinessScore(@Param("userId") Integer userId);

    @Query("SELECT CAST(s.submitted AS date) AS day, AVG(s.score) AS dailyAverage " +
            "FROM HappinessSurvey s " +
            "WHERE s.userId = :userId " +
            "AND s.submitted BETWEEN :startDate AND :endDate " +
            "GROUP BY day")
    List<Object[]> findDailyAveragesByUserIdAndDateRange(@Param("userId") Integer userId,
                                                         @Param("startDate") LocalDateTime startDate,
                                                         @Param("endDate") LocalDateTime endDate);

}
