package ar.com.ada.sb.api.film.model.repository;

import ar.com.ada.sb.api.film.model.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("directorRepository")
public interface DirectorRepository extends JpaRepository<Director, Long> {
}
