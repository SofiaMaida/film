package ar.com.ada.sb.api.film.model.mapper;

import ar.com.ada.sb.api.film.model.dto.DirectorDTO;
import ar.com.ada.sb.api.film.model.entity.Director;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface DirectorMapper extends DataMapper<DirectorDTO, Director> {

    Director toEntity(DirectorDTO dto);

    DirectorDTO toDto(Director entity);

    default Director fromId(Long id) {
        if (id == null) return null;
        return new Director(id);
    }
}
