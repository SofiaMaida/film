package ar.com.ada.sb.api.film.model.mapper.circular.dependency;

import ar.com.ada.sb.api.film.model.dto.ActorDTO;
import ar.com.ada.sb.api.film.model.entity.Actor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ActorCycleMapper extends DataCycleMapper<ActorDTO, Actor> {

    ActorCycleMapper MAPPER = Mappers.getMapper(ActorCycleMapper.class);
}