package ar.com.ada.sb.api.film.model.mapper.circular.dependency;

import ar.com.ada.sb.api.film.model.dto.FilmDTO;
import ar.com.ada.sb.api.film.model.entity.Film;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FilmCycleMapper extends DataCycleMapper<FilmDTO, Film>{

    FilmCycleMapper MAPPER = Mappers.getMapper(FilmCycleMapper.class);
}
