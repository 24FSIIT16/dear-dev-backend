package ch.fhnw.deardevbackend.repositories;

import ch.fhnw.deardevbackend.entities.HappinessSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface HappinessSurveyRepository extends JpaRepository<HappinessSurvey, Integer> {

    @Query("SELECT o FROM HappinessSurvey o WHERE o.userId = :userId")
    List<HappinessSurvey> findByUserId(@Param("userId") Integer userId);

    @Query("SELECT AVG(o.score) FROM HappinessSurvey o WHERE o.userId = :userId")
    Double findAverageScoreByUserId(@Param("userId") Integer userId);

    @Query("SELECT CAST(s.submitted AS date) AS day, AVG(s.score) AS dailyAverage FROM HappinessSurvey s WHERE s.userId = :userId GROUP BY day")
    List<Object[]> findDailyAveragesByUserId(Integer userId);


    @Query("SELECT CAST(s.submitted AS date) AS day, AVG(s.score) AS dailyAverage " +
            "FROM HappinessSurvey s " +
            "WHERE s.userId = :userId " +
            "AND s.submitted BETWEEN :startDate AND :endDate " +
            "GROUP BY day")
    List<Object[]> findDailyAveragesByUserIdAndDateRange(@Param("userId") Integer userId,
                                                         @Param("startDate") LocalDateTime startDate,
                                                         @Param("endDate") LocalDateTime endDate);

}
