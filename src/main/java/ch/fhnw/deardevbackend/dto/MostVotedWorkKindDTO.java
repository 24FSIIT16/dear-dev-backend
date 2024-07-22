package ch.fhnw.deardevbackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MostVotedWorkKindDTO {
    private Integer workKindId;
    private Integer voteCount;
}