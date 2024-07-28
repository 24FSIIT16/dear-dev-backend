package ch.fhnw.deardevbackend.mapper;

import ch.fhnw.deardevbackend.dto.WorkKindInsightDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface WorkKindInsightMapper {
    WorkKindInsightMapper INSTANCE = Mappers.getMapper(WorkKindInsightMapper.class);

    default WorkKindInsightDTO toDTO(Object[] row) {
        if (row == null) {
            return null;
        }

        return new WorkKindInsightDTO(
                (Integer) row[0], // teamId
                (Integer) row[1], // workKindId
                (String) row[2],  // workKindName
                (Double) row[3],  // averageHappiness
                (Long) row[4]     // totalCount
        );
    }
}
