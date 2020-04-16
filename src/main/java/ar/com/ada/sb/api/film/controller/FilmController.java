package ar.com.ada.sb.api.film.controller;

import ar.com.ada.sb.api.film.model.dto.FilmDTO;
import ar.com.ada.sb.api.film.service.FilmServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping({"/films"})
public class FilmController {

    @Autowired @Qualifier("filmServices")
    private FilmServices filmServices;

    @GetMapping({"", "/"})
    public ResponseEntity getAllFilms() {
        List<FilmDTO> all = filmServices.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping({"/{id}", "/{id}/"})
    public ResponseEntity getFilmById(@PathVariable Long id) {
        FilmDTO filmById = filmServices.findFilmById(id);
        return ResponseEntity.ok(filmById);
    }

    @PostMapping({"", "/"})
    public ResponseEntity addNewFilm(@Valid @RequestBody FilmDTO filmDTO) throws URISyntaxException {
        FilmDTO filmSaved = filmServices.save(filmDTO);
        return ResponseEntity.created(new URI("/films/" + filmDTO.getId())).body(filmSaved);
    }

    @PutMapping({"", "/"})
    public ResponseEntity updateFilmById(@Valid @RequestBody FilmDTO filmDTO, @PathVariable Long id) {
        FilmDTO updateFilm = filmServices.updateFilm(filmDTO, id);
        return ResponseEntity.ok(updateFilm);
    }

    @DeleteMapping({"", "/"})
    public ResponseEntity deleteFilm(@PathVariable Long id) {
        filmServices.delete(id);
        return ResponseEntity.noContent().build();
    }

}
