package entities.mappers;

import dto.ClientDTO;
import entities.Client;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    Client mapToModel(ClientDTO clientDTO);
    ClientDTO mapToDTO(Client client);
}
