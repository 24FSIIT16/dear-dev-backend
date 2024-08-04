package ch.fhnw.deardevbackend.repositories;

import ch.fhnw.deardevbackend.entities.WorkKindSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface WorkKindSurveyRepository extends JpaRepository<WorkKindSurvey, Integer> {

    @Query("SELECT w.workKindId, COUNT(w.workKindId) as voteCount " +
            "FROM WorkKindSurvey w " +
            "WHERE w.userId = :userId " +
            "GROUP BY w.workKindId " +
            "ORDER BY voteCount DESC")
    List<Object[]> findWorkKindCountByUserId(Integer userId);

    @Query("SELECT AVG(w.score) " +
            "FROM WorkKindSurvey w " +
            "WHERE w.workKindId = :workKindId AND w.userId = :userId")
    Optional<Integer> findAverageHappinessScoreByWorkKindIdAndUserId(Integer workKindId, Integer userId);



//    @Query("SELECT COUNT(DISTINCT wk.id) FROM WorkKind wk WHERE wk.teamId = :teamId")
//    int findDistinctWorkKindCountByTeamId(@Param("teamId") Integer teamId);


    // works
    // Query to get the distinct workkind count and list of workkind IDs for a specific user on each day, no date range
    @Query("SELECT DATE_TRUNC('day', w.submitted) AS dateTime, " +
            "COUNT(DISTINCT w.workKindId) AS workKindCount " +
            "FROM WorkKindSurvey w " +
            "WHERE w.userId = :userId " +
            "GROUP BY DATE_TRUNC('day', w.submitted) " +
            "ORDER BY DATE_TRUNC('day', w.submitted)")
    List<Object[]> findWorkKindCountPerDayForUserNoDateRange(@Param("userId") Integer userId);



    // Query with date range using LocalDateTime for parameters and casting in the query
    @Query(value = "SELECT DATE_TRUNC('day', submitted) AS dateTime, " +
            "COUNT(DISTINCT work_kind_id) AS workKindCount " +
            "FROM work_kind_survey " +
            "WHERE user_id = :userId " +
            "AND submitted BETWEEN :startDate AND :endDate " +
            "GROUP BY DATE_TRUNC('day', submitted) " +
            "ORDER BY DATE_TRUNC('day', submitted)",
            nativeQuery = true)
    List<Object[]> findWorkKindCountPerDayForUserWithDateRange(
            @Param("userId") Integer userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);


}


