package ch.fhnw.deardevbackend.mapper;

import ch.fhnw.deardevbackend.dto.TeamAndRoleDTO;
import ch.fhnw.deardevbackend.entities.Role;
import ch.fhnw.deardevbackend.entities.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TeamAndRoleMapper {
    TeamAndRoleMapper INSTANCE = Mappers.getMapper(TeamAndRoleMapper.class);

    @Mapping(source = "team.id", target = "id")
    @Mapping(source = "team.name", target = "name")
    @Mapping(source = "team.currentSprintId", target = "currentSprintId")
    @Mapping(source = "team.configId", target = "configId")
    @Mapping(source = "team.code", target = "code")
    @Mapping(source = "team.createdBy", target = "createdBy")
    @Mapping(source = "team.createdAt", target = "createdAt")
    @Mapping(source = "team.active", target = "active")
    @Mapping(source = "role", target = "role")
    TeamAndRoleDTO toDTO(Team team, Role role);
}
