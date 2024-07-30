package ch.fhnw.deardevbackend.mapper;

import ch.fhnw.deardevbackend.dto.TeamConfigDTO;
import ch.fhnw.deardevbackend.dto.WorkKindDTO;
import ch.fhnw.deardevbackend.entities.Team;
import ch.fhnw.deardevbackend.entities.TeamConfig;
import ch.fhnw.deardevbackend.entities.WorkKind;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface TeamConfigMapper {
    TeamConfigMapper INSTANCE = Mappers.getMapper(TeamConfigMapper.class);

    @Mapping(source = "team.name", target = "teamName")
    @Mapping(source = "config.id", target = "id")
    @Mapping(source = "workKinds", target = "workKinds")
    @Mapping(source = "config.happinessSurvey", target = "happinessSurvey")
    @Mapping(source = "config.workKindSurvey", target = "workKindSurvey")
    @Mapping(source = "config.emotionSurvey", target = "emotionSurvey")
    TeamConfigDTO toTeamConfigDTO(TeamConfig config, Team team, List<WorkKindDTO> workKinds);

    WorkKindDTO toWorkKindDTO(WorkKind workKind);

    WorkKind toWorkKind(WorkKindDTO workKindDTO);
}
