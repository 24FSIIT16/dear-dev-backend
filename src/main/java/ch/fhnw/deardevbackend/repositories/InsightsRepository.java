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
public interface InsightsRepository extends JpaRepository<HappinessSurvey, Integer> {

    @Query("SELECT wk.teamId, ws.workKindId, wk.name as workKindName, AVG(ws.score) as averageHappiness, COUNT(ws.workKindId) as totalCount " +
            "FROM WorkKindSurvey ws " +
            "JOIN WorkKind wk ON ws.workKindId = wk.id " +
            "WHERE ws.userId = :userId " +
            "GROUP BY wk.teamId, ws.workKindId, wk.name " +
            "ORDER BY wk.teamId, ws.workKindId")
    List<Object[]> findWorkKindHappinessByUserId(Integer userId);

    @Query("SELECT CAST(submitted AS DATE) as day, AVG(score) as average " +
            "FROM HappinessSurvey " +
            "WHERE userId IN (SELECT userId FROM TeamMember WHERE teamId = ?1) " +
            "AND userId != ?2 " +
            "GROUP BY day")
    List<Object[]> findTeamDailyAveragesExcludingUser(Integer teamId, Integer userId);

    @Query("SELECT CAST(h.submitted AS DATE) as day, AVG(h.score) as average " +
            "FROM HappinessSurvey h " +
            "WHERE h.userId IN (SELECT tm.userId FROM TeamMember tm WHERE tm.teamId = :teamId) " +
            "AND h.userId != :userId " +
            "AND h.submitted BETWEEN :startDate AND :endDate " +
            "GROUP BY day")
    List<Object[]> findTeamDailyAveragesExcludingUserAndDateRange(@Param("teamId") Integer teamId,
                                                                  @Param("userId") Integer userId,
                                                                  @Param("startDate") LocalDateTime startDate,
                                                                  @Param("endDate") LocalDateTime endDate);
}
