package ch.fhnw.deardevbackend.dto;

import lombok.Data;

@Data
public class CreateTeamDTO {
    private String name;
    private int userId;
}
