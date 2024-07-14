package ch.fhnw.deardevbackend.mapper;

import ch.fhnw.deardevbackend.dto.UserDTO;
import ch.fhnw.deardevbackend.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "user.id", target = "id")
    @Mapping(source = "user.name", target = "name")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "provider", target = "loginProvider")
    @Mapping(source = "hasTeam", target = "hasTeam")
    UserDTO toDto(User user, String provider, Boolean hasTeam);
}
