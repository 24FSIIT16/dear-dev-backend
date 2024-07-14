package ch.fhnw.deardevbackend.mapper;

import ch.fhnw.deardevbackend.dto.CreateTeamDTO;
import ch.fhnw.deardevbackend.entities.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CreateTeamMapper {
    CreateTeamMapper INSTANCE = Mappers.getMapper(CreateTeamMapper.class);

    @Mapping(target = "currentSprintId", ignore = true)
    @Mapping(target = "configId", ignore = true)
    @Mapping(target = "createdBy", source = "userId")
    @Mapping(target = "createdAt", expression = "java(java.time.OffsetDateTime.now())")
    @Mapping(target = "active", constant = "true")
    Team toTeam(CreateTeamDTO createTeamDTO);
}
