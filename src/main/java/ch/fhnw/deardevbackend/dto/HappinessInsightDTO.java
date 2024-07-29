package ch.fhnw.deardevbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HappinessInsightDTO {
    private String day;
    private double userAverage;
    private double teamAverage;
}
