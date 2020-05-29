package ar.com.ada.sb.api.film.service;

import ar.com.ada.sb.api.film.component.BusinessLogicExceptionComponent;
import ar.com.ada.sb.api.film.exception.ApiEntityError;
import ar.com.ada.sb.api.film.exception.BusinessLogicException;
import ar.com.ada.sb.api.film.model.dto.FilmDTO;
import ar.com.ada.sb.api.film.model.entity.Actor;
import ar.com.ada.sb.api.film.model.entity.Director;
import ar.com.ada.sb.api.film.model.entity.Film;
import ar.com.ada.sb.api.film.model.mapper.circular.dependency.CycleAvoidingMappingContext;
import ar.com.ada.sb.api.film.model.mapper.circular.dependency.FilmCycleMapper;
import ar.com.ada.sb.api.film.model.repository.ActorRepository;
import ar.com.ada.sb.api.film.model.repository.DirectorRepository;
import ar.com.ada.sb.api.film.model.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("filmServices")
public class FilmServices implements Services<FilmDTO> {

    @Autowired @Qualifier("businessLogicExceptionComponent")
    private BusinessLogicExceptionComponent logicExceptionComponent;

    @Autowired @Qualifier("filmRepository")
    private FilmRepository filmRepository;

    @Autowired @Qualifier("actorRepository")
    private ActorRepository actorRepository;

    @Autowired @Qualifier("directorRepository")
    private DirectorRepository directorRepository;

    private FilmCycleMapper filmCycleMapper = FilmCycleMapper.MAPPER;

    @Autowired @Qualifier("cycleAvoidingMappingContext")
    private CycleAvoidingMappingContext context;

    @Override
    public List<FilmDTO> findAll() {
        List<Film> all = filmRepository.findAll();
        List<FilmDTO> filmDTOList = filmCycleMapper.toDto(all, context);
        return filmDTOList;
    }

    public FilmDTO findFilmById(Long id) {
        Optional<Film> byIdOptional = filmRepository.findById(id);
        FilmDTO filmDTO = null;

        if (byIdOptional.isPresent()) {
            Film filmById = byIdOptional.get();
            filmDTO = filmCycleMapper.toDto(filmById, context);
        } else {
            logicExceptionComponent.throwExceptionEntityNotFound("Film", id);
        }
        return filmDTO;
    }

    @Override
    public FilmDTO save(FilmDTO dto) {
        Film filmToSave = filmCycleMapper.toEntity(dto, context);
        Film filmSaved = filmRepository.save(filmToSave);
        FilmDTO filmDTOSaved = filmCycleMapper.toDto(filmSaved, context);
        return filmDTOSaved;
    }

    public FilmDTO updateFilm(FilmDTO filmDTOToUpdate, Long id) {
        Optional<Film> byIdOptional = filmRepository.findById(id);
        FilmDTO filmDTOUpdated = null;

        if (byIdOptional.isPresent()) {
            Film filmById = byIdOptional.get();
            filmDTOToUpdate.setId(filmById.getId());
            Film filmToUpdate = filmCycleMapper.toEntity(filmDTOToUpdate, context);
            Film filmUpdated = filmRepository.save(filmToUpdate);
            filmDTOToUpdate = filmCycleMapper.toDto(filmUpdated, context);
        } else {
            logicExceptionComponent.throwExceptionEntityNotFound("Film", id);
        }
        return filmDTOToUpdate;
    }

    @Override
    public void delete(Long id) {
        Optional<Film> byIdOptional = filmRepository.findById(id);

        if (byIdOptional.isPresent()) {
            Film filmToDelete = byIdOptional.get();
            filmRepository.delete(filmToDelete);
        } else {
            logicExceptionComponent.throwExceptionEntityNotFound("Film", id);
        }
    }

    public FilmDTO addActorToFilm(Long actorId, Long filmId) {
        //Busca la pelicula
        Optional<Film> filmByIdOptional = filmRepository.findById(filmId);
        //Busca el actor
        Optional<Actor> actorByIdOptional = actorRepository.findById(actorId);
        FilmDTO filmDTOWithNewActor = null;

        //Se verifica si el actor y la pelicula existen - Se verifica si el registro existe
        if (!filmByIdOptional.isPresent())
            logicExceptionComponent.throwExceptionEntityNotFound("Film", filmId);

        if (!actorByIdOptional.isPresent())
            logicExceptionComponent.throwExceptionEntityNotFound("Actor", actorId);

        //Extraigo el actor y la pelicula que quiero agregar
        Film film = filmByIdOptional.get();
        Actor actorToAdd = actorByIdOptional.get();

        //De la pelicula obtiene la lista de actores
        boolean hasActorInFilm = film.getActors()
                                .stream()
                //Busca en la lista el nombre del actor sea igual al nombre que deseo agregar
                                .anyMatch(actor -> actor.getName().equals(actorToAdd.getName()));

        //Si da false ningun actor existe con el nombre que busco en la BD
        if (!hasActorInFilm) {
            //Se agrega a la pelicula el actor que deseo
            film.addActor(actorToAdd);
            //Despues de agregar, guardo la informacion -> .save(film) film = tiene la info del actor
            Film filmWithNewActor = filmRepository.save(film);
            //Se devuelve al controlador
            filmDTOWithNewActor = filmCycleMapper.toDto(filmWithNewActor, context);
        } else {
            ApiEntityError apiEntityError = new ApiEntityError(
                    "Actor",
                    "AlreadyExist",
                    "The Actor with id '" + actorId + "' already exist in the film"
            );
            throw new BusinessLogicException(
                    "Actor already exist in the film",
                    HttpStatus.BAD_REQUEST,
                    apiEntityError
            );
        }
        return filmDTOWithNewActor;
    }

    public FilmDTO addDirectorToFilm(Long directorId, Long filmId) {
        Optional<Film> filmByIdOptional = filmRepository.findById(filmId);
        Optional<Director> directorByIdOptional = directorRepository.findById(directorId);
        FilmDTO filmDtoWithDirector = null;

        if (!filmByIdOptional.isPresent())
            logicExceptionComponent.throwExceptionEntityNotFound("Film", filmId);

        if (!directorByIdOptional.isPresent())
            logicExceptionComponent.throwExceptionEntityNotFound("Director", directorId);

        Film film = filmByIdOptional.get();
        Director directorToSet = directorByIdOptional.get();

        film.setDirector(directorToSet);
        Film filmWithDirector = filmRepository.save(film);
        filmDtoWithDirector = filmCycleMapper.toDto(filmWithDirector, context);

        return filmDtoWithDirector;
    }


}
