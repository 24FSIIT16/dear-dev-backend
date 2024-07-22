package ch.fhnw.deardevbackend.repositories;

import ch.fhnw.deardevbackend.entities.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Integer> {
    @Query(
            value = "select count(*) > 0 from team_member where user_id =:userId",
            nativeQuery = true)
    Boolean userIsInTeam(@Param("userId") Integer userId);

    List<TeamMember> findByUserId(Integer userId);

    @Query("SELECT tm.teamId FROM TeamMember tm WHERE tm.userId = :userId AND tm.active = true")
    List<Integer> findTeamIdByUserId(@Param("userId") Integer userId);
}
