package ar.com.ada.sb.api.film.service;

import ar.com.ada.sb.api.film.exception.ApiEntityError;
import ar.com.ada.sb.api.film.exception.BusinessLogicException;
import ar.com.ada.sb.api.film.model.dto.ActorDTO;
import ar.com.ada.sb.api.film.model.entity.Actor;
import ar.com.ada.sb.api.film.model.mapper.ActorMapper;
import ar.com.ada.sb.api.film.model.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("actorServices")
public class ActorServices implements Services<ActorDTO> {

    @Autowired @Qualifier("actorRepository")
    private ActorRepository actorRepository;

    private ActorMapper actorMapper;

    public ActorServices(ActorMapper actorMapper) {
        this.actorMapper = actorMapper;
    }

    @Override
    public List<ActorDTO> findAll() {
        List<Actor> actorsEntityList = actorRepository.findAll();
        List<ActorDTO> actorsDtoList = actorMapper.toDto(actorsEntityList);
        return actorsDtoList;
    }

    public ActorDTO findActorById(Long id) {
        // SELECT * FROM Actor WHERE id = ?
        Optional<Actor> byIdOptional = actorRepository.findById(id);
        ActorDTO actorDTO = null;

        if (byIdOptional.isPresent()) {
            Actor actorById = byIdOptional.get();
            actorDTO = actorMapper.toDto(actorById);
        } else {
            throwBusinessLogicException(id);
        }

        return actorDTO;
    }

    @Override
    public ActorDTO save(ActorDTO dto) {
        Actor actorToSave = actorMapper.toEntity(dto);
        Actor actorSaved = actorRepository.save(actorToSave);
        ActorDTO actorDtoSaved = actorMapper.toDto(actorSaved);
        return actorDtoSaved;
    }

    public ActorDTO updateActor(ActorDTO actorDtoToUpdate, Long id) {
        Optional<Actor> byIdOptional = actorRepository.findById(id);
        ActorDTO actorDtoUpdated = null;

        if (byIdOptional.isPresent()) {
            Actor actorById = byIdOptional.get();
            actorDtoToUpdate.setId(actorById.getId());
            Actor actorToUpdate = actorMapper.toEntity(actorDtoToUpdate);
            Actor actorUpdated = actorRepository.save(actorToUpdate);
            actorDtoUpdated = actorMapper.toDto(actorUpdated);
        } else {
            throwBusinessLogicException(id);
        }

        return actorDtoUpdated;
    }

    @Override
    public void delete(Long id) {
        Optional<Actor> byIdOptional = actorRepository.findById(id);

        if (byIdOptional.isPresent()) {
            Actor actorToDelete = byIdOptional.get();
            actorRepository.delete(actorToDelete);
        } else {
            throwBusinessLogicException(id);
        }
    }

    private void throwBusinessLogicException(Long id) {
        ApiEntityError apiEntityError = new ApiEntityError(
                "Actor",
                "NotFound",
                "The actor with id '" + id + "' does not exist"
        );

        throw new BusinessLogicException(
                "actor does not exist",
                HttpStatus.NOT_FOUND,
                apiEntityError
        );
    }
}