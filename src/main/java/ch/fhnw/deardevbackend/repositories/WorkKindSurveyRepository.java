package ch.fhnw.deardevbackend.repositories;

import ch.fhnw.deardevbackend.entities.WorkKindSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface WorkKindSurveyRepository extends JpaRepository<WorkKindSurvey, Integer> {

    @Query(value = "SELECT wk.name FROM work_kind_survey ws " +
            "JOIN work_kind wk ON ws.work_kind_id = wk.id " +
            "WHERE ws.user_id = :userId " +
            "GROUP BY wk.name " +
            "ORDER BY COUNT(wk.name) DESC " +
            "LIMIT 1", nativeQuery = true)
    String findMostTrackedWorkType(@Param("userId") Integer userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM WorkKindSurvey ws WHERE ws.userId = :userId")
    void deleteByUserId(Integer userId);
}
