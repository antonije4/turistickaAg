package entities.mappers;

import dto.CategorizationRequestRow;
import dto.UgostiteljskiObjekatDTO;
import entities.UgostiteljskiObjekat;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper
public interface UgostiteljskiObjekatMapper {
    UgostiteljskiObjekatMapper INSTANCE = Mappers.getMapper(UgostiteljskiObjekatMapper.class);

    UgostiteljskiObjekatDTO mapToDTO(UgostiteljskiObjekat objekat);
    UgostiteljskiObjekat mapToEntity(UgostiteljskiObjekatDTO objekatDTO);
    List<CategorizationRequestRow> ugostiteljskiObjekatListToCategorizationRow(Set<UgostiteljskiObjekat> ugostiteljskiObjekatList);
}
