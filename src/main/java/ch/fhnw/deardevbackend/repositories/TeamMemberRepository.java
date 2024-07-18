package ch.fhnw.deardevbackend.repositories;

import ch.fhnw.deardevbackend.entities.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Integer> {
    @Query(
            value = "select count(*) > 0 from team_member where user_id =:userId",
            nativeQuery = true)
    Boolean userIsInTeam(@Param("userId") Integer userId);
}
