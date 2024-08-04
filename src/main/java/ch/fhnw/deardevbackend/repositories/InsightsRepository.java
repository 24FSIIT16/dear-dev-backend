package ch.fhnw.deardevbackend.repositories;

import ch.fhnw.deardevbackend.entities.HappinessSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface InsightsRepository extends JpaRepository<HappinessSurvey, Integer> {

    // overall happiness - no daterange
    @Query("SELECT CAST(submitted AS DATE) as day, AVG(score) as average " +
            "FROM HappinessSurvey " +
            "WHERE userId IN (SELECT userId FROM TeamMember WHERE teamId = ?1) " +
            "AND userId != ?2 " +
            "GROUP BY day")
    List<Object[]> findTeamDailyAveragesExcludingUser(Integer teamId, Integer userId);

    // overall happiness - with daterange
    @Query("SELECT CAST(h.submitted AS DATE) as day, AVG(h.score) as average " +
            "FROM HappinessSurvey h " +
            "WHERE h.userId IN (SELECT tm.userId FROM TeamMember tm WHERE tm.teamId = :teamId) " +
            "AND h.userId != :userId " +
            "AND h.submitted BETWEEN :startDate AND :endDate " +
            "GROUP BY day")
    List<Object[]> findTeamDailyAveragesExcludingUserAndDateRange(@Param("teamId") Integer teamId,
                                                                  @Param("userId") Integer userId,
                                                                  @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // work kinds for a user with date range
    @Query("SELECT wk.id as workKindId, wk.name as workKindName, " +
            "AVG(ws.score) as userAverage, COUNT(ws.workKindId) as userCount " +
            "FROM WorkKindSurvey ws " +
            "JOIN WorkKind wk ON ws.workKindId = wk.id " +
            "WHERE ws.userId = :userId " +
            "AND ws.submitted BETWEEN :startDate AND :endDate " +
            "GROUP BY wk.id, wk.name " +
            "ORDER BY userCount DESC, userAverage DESC")
    List<Object[]> findTopWorkKindsByUserAndDateRange(@Param("userId") Integer userId,
                                                      @Param("startDate") LocalDateTime startDate,
                                                      @Param("endDate") LocalDateTime endDate);

    // work kinds for a team with date range (excluding the specific user)
    @Query("SELECT wk.id as workKindId, wk.name as workKindName, " +
            "AVG(ws.score) as teamAverage, COUNT(ws.workKindId) as teamCount " +
            "FROM WorkKindSurvey ws " +
            "JOIN WorkKind wk ON ws.workKindId = wk.id " +
            "WHERE ws.userId IN (SELECT tm.userId FROM TeamMember tm WHERE tm.teamId = :teamId) " +
            "AND ws.userId != :userId " +
            "AND ws.submitted BETWEEN :startDate AND :endDate " +
            "GROUP BY wk.id, wk.name " +
            "ORDER BY teamCount DESC, teamAverage DESC")
    List<Object[]> findTopWorkKindsByTeamAndDateRange(@Param("teamId") Integer teamId,
                                                      @Param("userId") Integer userId,
                                                      @Param("startDate") LocalDateTime startDate,
                                                      @Param("endDate") LocalDateTime endDate);

    // work kinds for a user without date range
    @Query("SELECT wk.id as workKindId, wk.name as workKindName, " +
            "AVG(ws.score) as userAverage, COUNT(ws.workKindId) as userCount " +
            "FROM WorkKindSurvey ws " +
            "JOIN WorkKind wk ON ws.workKindId = wk.id " +
            "WHERE ws.userId = :userId " +
            "GROUP BY wk.id, wk.name " +
            "ORDER BY userCount DESC, userAverage DESC")
    List<Object[]> findTopWorkKindsByUser(@Param("userId") Integer userId);

    // work kinds for a team without date range (excluding the specific user)
    @Query("SELECT wk.id as workKindId, wk.name as workKindName, " +
            "AVG(ws.score) as teamAverage, COUNT(ws.workKindId) as teamCount " +
            "FROM WorkKindSurvey ws " +
            "JOIN WorkKind wk ON ws.workKindId = wk.id " +
            "WHERE ws.userId IN (SELECT tm.userId FROM TeamMember tm WHERE tm.teamId = :teamId) " +
            "AND ws.userId != :userId " +
            "GROUP BY wk.id, wk.name " +
            "ORDER BY teamCount DESC, teamAverage DESC")
    List<Object[]> findTopWorkKindsByTeam(@Param("teamId") Integer teamId, @Param("userId") Integer userId);


    // Fetch most voted emotions for the user with date range
    @Query("SELECT e.emotionId, em.name, COUNT(e.emotionId) as userCount " +
            "FROM EmotionSurvey e " +
            "JOIN Emotion em ON e.emotionId = em.id " +
            "WHERE e.userId = :userId " +
            "AND e.submitted BETWEEN :startDate AND :endDate " +
            "GROUP BY e.emotionId, em.name " +
            "ORDER BY userCount DESC")
    List<Object[]> findTopEmotionsByUserAndDateRange(@Param("userId") Integer userId,
                                                     @Param("startDate") LocalDateTime startDate,
                                                     @Param("endDate") LocalDateTime endDate);

    // Fetch most voted emotions for the team with date range
    @Query("SELECT e.emotionId, em.name, COUNT(e.emotionId) as teamCount " +
            "FROM EmotionSurvey e " +
            "JOIN Emotion em ON e.emotionId = em.id " +
            "WHERE e.userId IN (SELECT tm.userId FROM TeamMember tm WHERE tm.teamId = :teamId) " +
            "AND e.userId != :userId " +
            "AND e.submitted BETWEEN :startDate AND :endDate " +
            "GROUP BY e.emotionId, em.name " +
            "ORDER BY teamCount DESC")
    List<Object[]> findTopEmotionsByTeamAndDateRange(@Param("teamId") Integer teamId,
                                                     @Param("userId") Integer userId,
                                                     @Param("startDate") LocalDateTime startDate,
                                                     @Param("endDate") LocalDateTime endDate);

    // Fetch most voted emotions for the user without date range
    @Query("SELECT e.emotionId, em.name, COUNT(e.emotionId) as userCount " +
            "FROM EmotionSurvey e " +
            "JOIN Emotion em ON e.emotionId = em.id " +
            "WHERE e.userId = :userId " +
            "GROUP BY e.emotionId, em.name " +
            "ORDER BY userCount DESC")
    List<Object[]> findTopEmotionsByUser(@Param("userId") Integer userId);

    // Fetch most voted emotions for the team without date range
    @Query("SELECT e.emotionId, em.name, COUNT(e.emotionId) as teamCount " +
            "FROM EmotionSurvey e " +
            "JOIN Emotion em ON e.emotionId = em.id " +
            "WHERE e.userId IN (SELECT tm.userId FROM TeamMember tm WHERE tm.teamId = :teamId) " +
            "AND e.userId != :userId " +
            "GROUP BY e.emotionId, em.name " +
            "ORDER BY teamCount DESC")
    List<Object[]> findTopEmotionsByTeam(@Param("teamId") Integer teamId, @Param("userId") Integer userId);




//   sdfsdfsdfsdf



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

    @Query(value = "SELECT DATE_TRUNC('day', submitted) AS dateTime, " +
            "COUNT(DISTINCT work_kind_id) AS workKindCount, AVG(score) AS teamAverageHappiness " +
            "FROM work_kind_survey " +
            "WHERE user_id IN (SELECT tm.user_id FROM team_member tm WHERE tm.team_id = :teamId) " +
            "AND submitted BETWEEN :startDate AND :endDate " +
            "GROUP BY DATE_TRUNC('day', submitted) " +
            "ORDER BY DATE_TRUNC('day', submitted)",
            nativeQuery = true)
    List<Object[]> findTeamWorkKindCountPerDayWithDateRange(
            @Param("teamId") Integer teamId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT DATE_TRUNC('day', submitted) AS dateTime, " +
            "COUNT(DISTINCT work_kind_id) AS workKindCount, AVG(score) AS teamAverageHappiness " +
            "FROM work_kind_survey " +
            "WHERE user_id IN (SELECT tm.user_id FROM team_member tm WHERE tm.team_id = :teamId) " +
            "GROUP BY DATE_TRUNC('day', submitted) " +
            "ORDER BY DATE_TRUNC('day', submitted)",
            nativeQuery = true)
    List<Object[]> findTeamWorkKindCountPerDayNoDateRange(@Param("teamId") Integer teamId);




}
