package entities.mappers;

import dto.UgostiteljDTO;
import entities.*;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface UgostiteljMapper {
    UgostiteljMapper INSTANCE = Mappers.getMapper(UgostiteljMapper.class);

    UgostiteljDTO mapToDTO(Ugostitelj ugostitelj);
    Ugostitelj mapToModel(UgostiteljDTO ugostiteljDTO);

    default List<UgostiteljDTO> mapUgostiteljList(List<Ugostitelj> ugostiteljList) {
        List<UgostiteljDTO> dtoList = new ArrayList<>();
        ugostiteljList.forEach(ugostitelj -> {
            UgostiteljDTO ugostiteljDto = mapToDTO(ugostitelj);
            addSpecificFields(ugostiteljDto, ugostitelj);
            dtoList.add(ugostiteljDto);
        });
        return dtoList;
    }

    Ustanova mergeUstanova(@MappingTarget Ustanova ustanova, Ugostitelj ugostitelj);
    Preduzetnik mergePreduzetnik(@MappingTarget Preduzetnik preduzetnik, Ugostitelj ugostitelj);
    PravnoLice mergePravnoLice(@MappingTarget PravnoLice pravnoLice, Ugostitelj ugostitelj);
    FizickoLice mergeFizicikoLice(@MappingTarget FizickoLice fizickoLice, Ugostitelj ugostitelj);

    default void addSpecificFields(UgostiteljDTO ugostiteljDTO, Ugostitelj ugostitelj) {
        switch (ugostiteljDTO.getTipUgostitelja()) {
            case FizickoLice:
                ugostiteljDTO.setIme(((FizickoLice) ugostitelj).getIme());
                ugostiteljDTO.setPrezime(((FizickoLice) ugostitelj).getPrezime());
                break;
            case Preduzetnik:
                ugostiteljDTO.setIme(((Preduzetnik) ugostitelj).getIme());
                ugostiteljDTO.setPrezime(((Preduzetnik) ugostitelj).getPrezime());
                break;
            case PravnoLice:
                ugostiteljDTO.setNaziv(((PravnoLice) ugostitelj).getNazivFirme());
                break;
            case Ustanova:
                ugostiteljDTO.setNaziv(((Ustanova) ugostitelj).getNaziv());
                break;
            default:
                break;
        }
    }
}
