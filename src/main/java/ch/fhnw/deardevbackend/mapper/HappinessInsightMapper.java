package ch.fhnw.deardevbackend.mapper;



import ch.fhnw.deardevbackend.dto.HappinessInsightDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface HappinessInsightMapper {
    HappinessInsightMapper INSTANCE = Mappers.getMapper(HappinessInsightMapper.class);

    @Mapping(target = "day", source = "day")
    @Mapping(target = "userAverage", source = "userAverage")
    @Mapping(target = "teamAverage", source = "teamAverage")
    HappinessInsightDTO toDTO(String day, double userAverage, double teamAverage);
}
