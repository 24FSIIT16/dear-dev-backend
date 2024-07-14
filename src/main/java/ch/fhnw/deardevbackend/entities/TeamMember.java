package ch.fhnw.deardevbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "team_member")
@Builder
@Data
public class TeamMember {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "team_id")
    private int teamId;

    @Column(name = "role")
    private String role;

    @Column(name = "active")
    private boolean active;
}
