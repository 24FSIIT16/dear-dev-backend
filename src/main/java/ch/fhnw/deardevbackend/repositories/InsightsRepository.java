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


    // Fetch user work kind count and average happiness by day with date range
    @Query("SELECT COUNT(DISTINCT wks.workKindId) AS workKindCount, AVG(hs.score) AS userAverageHappiness " +
            "FROM WorkKindSurvey wks " +
            "JOIN HappinessSurvey hs ON wks.userId = hs.userId " +
            "AND CAST(wks.submitted AS DATE) = CAST(hs.submitted AS DATE) " +
            "WHERE wks.userId = :userId " +
            "AND wks.submitted BETWEEN :startDate AND :endDate " +
            "GROUP BY CAST(wks.submitted AS DATE) " +
            "ORDER BY workKindCount")
    List<Object[]> findUserWorkKindCountAndHappinessByDateRange(@Param("userId") Integer userId,
                                                                @Param("startDate") LocalDateTime startDate,
                                                                @Param("endDate") LocalDateTime endDate);

    // Fetch team work kind count and average happiness by day with date range
    @Query("SELECT COUNT(DISTINCT wks.workKindId) AS workKindCount, AVG(hs.score) AS teamAverageHappiness " +
            "FROM WorkKindSurvey wks " +
            "JOIN HappinessSurvey hs ON wks.userId = hs.userId " +
            "AND CAST(wks.submitted AS DATE) = CAST(hs.submitted AS DATE) " +
            "WHERE wks.userId IN (SELECT tm.userId FROM TeamMember tm WHERE tm.teamId = :teamId) " +
            "AND wks.userId != :userId " +
            "AND wks.submitted BETWEEN :startDate AND :endDate " +
            "GROUP BY CAST(wks.submitted AS DATE) " +
            "ORDER BY workKindCount")
    List<Object[]> findTeamWorkKindCountAndHappinessByDateRange(@Param("teamId") Integer teamId,
                                                                @Param("userId") Integer userId,
                                                                @Param("startDate") LocalDateTime startDate,
                                                                @Param("endDate") LocalDateTime endDate);

    // Fetch user work kind count and average happiness by day without date range
    @Query("SELECT COUNT(DISTINCT wks.workKindId) AS workKindCount, AVG(hs.score) AS userAverageHappiness " +
            "FROM WorkKindSurvey wks " +
            "JOIN HappinessSurvey hs ON wks.userId = hs.userId " +
            "AND CAST(wks.submitted AS DATE) = CAST(hs.submitted AS DATE) " +
            "WHERE wks.userId = :userId " +
            "GROUP BY CAST(wks.submitted AS DATE) " +
            "ORDER BY workKindCount")
    List<Object[]> findUserWorkKindCountAndHappiness(@Param("userId") Integer userId);

    // Fetch team work kind count and average happiness by day without date range
    @Query("SELECT COUNT(DISTINCT wks.workKindId) AS workKindCount, AVG(hs.score) AS teamAverageHappiness " +
            "FROM WorkKindSurvey wks " +
            "JOIN HappinessSurvey hs ON wks.userId = hs.userId " +
            "AND CAST(wks.submitted AS DATE) = CAST(hs.submitted AS DATE) " +
            "WHERE wks.userId IN (SELECT tm.userId FROM TeamMember tm WHERE tm.teamId = :teamId) " +
            "AND wks.userId != :userId " +
            "GROUP BY CAST(wks.submitted AS DATE) " +
            "ORDER BY workKindCount")
    List<Object[]> findTeamWorkKindCountAndHappiness(@Param("teamId") Integer teamId, @Param("userId") Integer userId);

}
