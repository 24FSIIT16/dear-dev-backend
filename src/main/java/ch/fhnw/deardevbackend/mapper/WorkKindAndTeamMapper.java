package ch.fhnw.deardevbackend.mapper;

import ch.fhnw.deardevbackend.dto.WorkKindAndTeamDTO;
import ch.fhnw.deardevbackend.entities.WorkKind;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface WorkKindAndTeamMapper {

    WorkKindAndTeamMapper INSTANCE = Mappers.getMapper(WorkKindAndTeamMapper.class);

    @Mapping(target = "workKind", source = "workKind")
    @Mapping(target = "teamName", source = "teamName")
    WorkKindAndTeamDTO toDTO(WorkKind workKind, String teamName);
}
