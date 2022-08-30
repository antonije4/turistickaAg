package entities.mappers;

import dto.UgostiteljDTO;
import entities.Ugostitelj;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UgostiteljMapper {
    UgostiteljMapper INSTANCE = Mappers.getMapper(UgostiteljMapper.class);

    UgostiteljDTO mapToDTO(Ugostitelj ugostitelj);
    Ugostitelj mapToModel(UgostiteljDTO ugostiteljDTO);
    List<UgostiteljDTO> mapUgostiteljListToDTOList(List<Ugostitelj> ugostiteljList);
}
