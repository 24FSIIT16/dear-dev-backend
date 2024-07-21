package ch.fhnw.deardevbackend.repositories;

import ch.fhnw.deardevbackend.entities.TeamMemberWithUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamMemberWithUserRepository extends JpaRepository<TeamMemberWithUser, Integer> {
    @Query("SELECT tm FROM TeamMemberWithUser tm JOIN FETCH tm.user WHERE tm.teamId = :teamId")
    List<TeamMemberWithUser> findByTeamId(int teamId);
}
