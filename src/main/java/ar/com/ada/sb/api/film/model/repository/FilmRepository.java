package ar.com.ada.sb.api.film.model.repository;

import ar.com.ada.sb.api.film.model.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("filmRepository")
public interface FilmRepository extends JpaRepository<Film, Long> {
}
