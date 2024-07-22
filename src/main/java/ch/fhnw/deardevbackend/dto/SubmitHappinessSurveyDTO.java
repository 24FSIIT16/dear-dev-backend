package ch.fhnw.deardevbackend.dto;

import lombok.Data;

@Data
public class SubmitHappinessSurveyDTO {
    private Integer userId;
    private Integer score;
}
