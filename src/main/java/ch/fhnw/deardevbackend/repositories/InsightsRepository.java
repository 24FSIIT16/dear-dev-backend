package ch.fhnw.deardevbackend.repositories;

import ch.fhnw.deardevbackend.entities.HappinessSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface InsightsRepository extends JpaRepository<HappinessSurvey, Integer> {


    @Query("SELECT CAST(submitted AS DATE) as day, AVG(score) as average " +
            "FROM HappinessSurvey " +
            "WHERE userId IN (SELECT userId FROM TeamMember WHERE userId IN (SELECT teamId FROM TeamMember WHERE userId = ?1) AND userId != ?1) " +
            "GROUP BY day")
    List<Object[]> findTeamDailyAveragesExcludingUser(Integer userId);


    @Query("SELECT wk.teamId, ws.workKindId, wk.name as workKindName, AVG(ws.score) as averageHappiness, COUNT(ws.workKindId) as totalCount " +
            "FROM WorkKindSurvey ws " +
            "JOIN WorkKind wk ON ws.workKindId = wk.id " +
            "WHERE ws.userId = :userId " +
            "GROUP BY wk.teamId, ws.workKindId, wk.name " +
            "ORDER BY wk.teamId, ws.workKindId")
    List<Object[]> findWorkKindHappinessByUserId(Integer userId);

}
