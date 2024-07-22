package ch.fhnw.deardevbackend.dto;

import lombok.Data;

@Data
public class SubmitWorkKindSurveyDTO {
    private Integer userId;
    private Integer score;
    private Integer workKindId;
}
