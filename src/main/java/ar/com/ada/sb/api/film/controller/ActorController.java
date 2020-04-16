package ar.com.ada.sb.api.film.controller;

import ar.com.ada.sb.api.film.model.dto.ActorDTO;
import ar.com.ada.sb.api.film.service.ActorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/actors")
public class ActorController {

    @Autowired @Qualifier("actorServices")
    private ActorServices actorServices;

    @GetMapping({"", "/"}) // localhost:8080/actors && localhost:8080/actors/ [GET]
    public ResponseEntity getAllActors() {
        List<ActorDTO> all = actorServices.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping({"/{id}", "/{id}/"}) // localhost:8080/actors/{id} && localhost:8080/actors/{id}/ [GET]
    public ResponseEntity getActorById(@PathVariable Long id) {
        ActorDTO actorById = actorServices.findActorById(id);
        return ResponseEntity.ok(actorById);
    }

    @PostMapping({"", "/"})
    public ResponseEntity addNewActor(@Valid @RequestBody ActorDTO actorDTO) throws URISyntaxException {
        ActorDTO actorSaved = actorServices.save(actorDTO);
        return ResponseEntity.created(new URI("/actors/" + actorDTO.getId())).body(actorSaved);
    }

    @PutMapping({ "/{id}", "/{id}/" })
    public ResponseEntity updateActorById(@Valid @RequestBody ActorDTO actorDto, @PathVariable Long id) {
        ActorDTO actorUpdated = actorServices.updateActor(actorDto, id);
        return ResponseEntity.ok(actorUpdated);
    }

    @DeleteMapping({"", "/"})
    public ResponseEntity deleteActor(@PathVariable Long id) {
        actorServices.delete(id);
        return ResponseEntity.noContent().build();
    }

}
