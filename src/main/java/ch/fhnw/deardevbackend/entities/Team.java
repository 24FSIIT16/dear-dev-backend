package ch.fhnw.deardevbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "team")
@Builder
@Data
public class Team {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "current_sprint_id")
    private Integer currentSprintId;

    @Column(name = "config_id")
    private Integer configId;

    @Column(name = "code")
    private String code;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "active")
    private boolean active;
}
