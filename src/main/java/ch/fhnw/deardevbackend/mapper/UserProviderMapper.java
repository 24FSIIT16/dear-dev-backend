package ch.fhnw.deardevbackend.mapper;

import ch.fhnw.deardevbackend.dto.UserAndProviderDTO;
import ch.fhnw.deardevbackend.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserProviderMapper {
    UserProviderMapper INSTANCE = Mappers.getMapper(UserProviderMapper.class);

    @Mapping(source = "user.id", target = "id")
    @Mapping(source = "user.name", target = "name")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "provider", target = "provider")
    UserAndProviderDTO toDto(User user, String provider);
}
