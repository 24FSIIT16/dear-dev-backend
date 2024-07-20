package ch.fhnw.deardevbackend.dto;

import lombok.Data;

@Data
public class SubmitWorkKindSurveyDTO {
    private int userId;
    private int score;
    private int workKindId;
}
