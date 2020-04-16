package ar.com.ada.sb.api.film.model.repository;

import ar.com.ada.sb.api.film.model.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("actorRepository")
public interface ActorRepository extends JpaRepository<Actor, Long> {

    // Optional<Actor> findByNameOrGender(String name, String gender);
    // SELECT * FROM Actor WHERE name = ? AND gender = ?
    // SELECT * FROM Actor WHERE name = ? OR gender = ?

}
