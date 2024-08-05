package ch.fhnw.deardevbackend.mapper;

import ch.fhnw.deardevbackend.dto.SprintDTO;
import ch.fhnw.deardevbackend.dto.TeamWithSprintsDTO;
import ch.fhnw.deardevbackend.entities.SprintConfig;
import ch.fhnw.deardevbackend.entities.Team;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TeamWithSprintsMapper {
    TeamWithSprintsMapper INSTANCE = Mappers.getMapper(TeamWithSprintsMapper.class);

    TeamWithSprintsDTO toDto(Team team, List<SprintDTO> sprints);

    List<SprintDTO> toSprintDtoList(List<SprintConfig> sprints);
}
