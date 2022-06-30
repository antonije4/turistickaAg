package entities.mappers;

import dto.UgostiteljDTO;
import entities.Ugostitelj;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UgostiteljMapper {
    UgostiteljMapper INSTANCE = Mappers.getMapper(UgostiteljMapper.class);

    UgostiteljDTO mapToDTO(Ugostitelj ugostitelj);
    Ugostitelj mapToModel(UgostiteljDTO ugostiteljDTO);
}
