package ar.com.ada.sb.api.film.model.mapper;

import ar.com.ada.sb.api.film.model.dto.ActorDTO;
import ar.com.ada.sb.api.film.model.entity.Actor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ActorMapper extends DataMapper<ActorDTO, Actor> {

    Actor toEntity(ActorDTO dto);

    ActorDTO toDto(Actor entity);

    default Actor fromId(Long id) {
        if (id == null) return null;
        return new Actor(id);
    }
}