package ar.com.ada.sb.api.film.model.mapper;


import java.util.List;

public interface DataMapper<D, E> {

    E toEntity(D DTO);

    D toDto(E entity);

    List<E> toEntity(List<D> dtoList);

    List<D> toDto(List<E> entityList);
}
