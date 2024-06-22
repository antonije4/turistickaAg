package entities.mappers;

import dto.ClientDTO;
import entities.Turista;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    Turista mapToModel(ClientDTO clientDTO);
    ClientDTO mapToDTO(Turista turista);
}
