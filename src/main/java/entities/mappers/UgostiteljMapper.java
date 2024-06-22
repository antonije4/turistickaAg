package entities.mappers;

import dto.UgostiteljDTO;
import entities.*;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UgostiteljMapper {
    UgostiteljMapper INSTANCE = Mappers.getMapper(UgostiteljMapper.class);

    UgostiteljDTO mapToDTO(Ugostitelj ugostitelj);
    Ugostitelj mapToModel(UgostiteljDTO ugostiteljDTO);
    List<UgostiteljDTO> mapUgostiteljListToDTOList(List<Ugostitelj> ugostiteljList);

    Ustanova mergeUstanova(@MappingTarget Ustanova ustanova, Ugostitelj ugostitelj);
    Preduzetnik mergePreduzetnik(@MappingTarget Preduzetnik preduzetnik, Ugostitelj ugostitelj);
    PravnoLice mergePravnoLice(@MappingTarget PravnoLice pravnoLice, Ugostitelj ugostitelj);
    FizickoLice mergeFizicikoLice(@MappingTarget FizickoLice fizickoLice, Ugostitelj ugostitelj);

}
