package ch.fhnw.deardevbackend.mapper;

import ch.fhnw.deardevbackend.dto.TeamWithMembersDTO;
import ch.fhnw.deardevbackend.entities.Team;
import ch.fhnw.deardevbackend.entities.TeamMemberWithUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface TeamWithMembersMapper {
    TeamWithMembersMapper INSTANCE = Mappers.getMapper(TeamWithMembersMapper.class);

    @Mapping(target = "team", source = "team")
    @Mapping(target = "members", source = "members")
    @Mapping(target = " isAdmin", source = "isAdmin")
    TeamWithMembersDTO toDTO(Team team, List<TeamMemberWithUser> members, Boolean isAdmin);
}
