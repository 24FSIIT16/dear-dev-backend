package ch.fhnw.deardevbackend.dto;

import ch.fhnw.deardevbackend.entities.Role;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class TeamAndRoleDTO {
    private int id;
    private String name;
    private Integer currentSprintId;
    private Integer configId;
    private String code;
    private Integer createdBy;
    private OffsetDateTime createdAt;
    private Boolean active;
    private Role role;
}
