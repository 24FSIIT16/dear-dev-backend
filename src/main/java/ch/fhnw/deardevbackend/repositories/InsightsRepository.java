package ch.fhnw.deardevbackend.repositories;

import ch.fhnw.deardevbackend.entities.HappinessSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface InsightsRepository extends JpaRepository<HappinessSurvey, Integer> {

    // todo do not hardccode user id!!
////
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

    @Query("SELECT CAST(submitted AS DATE) as day, AVG(score) as average " +
            "FROM HappinessSurvey " +
            "WHERE userId = ?1 " +
            "GROUP BY day")
    List<Object[]> findDailyAveragesByUserId(Integer userId);

//    @Query("SELECT w.teamId, w.workKindId, wk.name, AVG(w.score), COUNT(w.id) " +
//            "FROM WorkKindSurvey w " +
//            "JOIN WorkKind wk ON w.workKindId = wk.id " +
//            "WHERE w.userId = ?1 " +
//            "GROUP BY w.teamId, w.workKindId, wk.name")
//    List<Object[]> findWorkKindHappinessByUserId(Integer userId);

    @Query("SELECT CAST(submitted AS DATE) as day, AVG(score) as average " +
            "FROM HappinessSurvey " +
            "WHERE userId = ?2 " +
            "AND userId IN (SELECT userId FROM TeamMember WHERE teamId = ?1) " +
            "GROUP BY day")
    List<Object[]> findHappinessInsightsByTeam(Integer teamId, Integer userId);
}