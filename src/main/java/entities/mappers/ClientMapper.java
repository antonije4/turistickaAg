package entities.mappers;

import dto.ClientDTO;
import entities.Tourist;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    Tourist mapToModel(ClientDTO clientDTO);
    ClientDTO mapToDTO(Tourist tourist);
}
