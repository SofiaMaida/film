package ar.com.ada.sb.api.film.service;

import ar.com.ada.sb.api.film.component.BusinessLogicExceptionComponent;
import ar.com.ada.sb.api.film.model.dto.ActorDTO;
import ar.com.ada.sb.api.film.model.entity.Actor;
import ar.com.ada.sb.api.film.model.mapper.ActorMapper;
import ar.com.ada.sb.api.film.model.mapper.circular.dependency.ActorCycleMapper;
import ar.com.ada.sb.api.film.model.mapper.circular.dependency.CycleAvoidingMappingContext;
import ar.com.ada.sb.api.film.model.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("actorServices")
public class ActorServices implements Services<ActorDTO> {

    @Autowired @Qualifier("businessLogicExceptionComponent")
    private BusinessLogicExceptionComponent logicExceptionComponent;

    @Autowired @Qualifier("actorRepository")
    private ActorRepository actorRepository;

    private ActorCycleMapper actorCycleMapper = ActorCycleMapper.MAPPER;

    @Autowired @Qualifier("cycleAvoidingMappingContext")
    private CycleAvoidingMappingContext context;

    //private ActorMapper actorMapper;

    @Override
    public List<ActorDTO> findAll() {
        List<Actor> actorsEntityList = actorRepository.findAll();
        List<ActorDTO> actorsDtoList = actorCycleMapper.toDto(actorsEntityList, context);
        return actorsDtoList;
    }

    public ActorDTO findActorById(Long id) {
        // SELECT * FROM Actor WHERE id = ?
        Optional<Actor> byIdOptional = actorRepository.findById(id);
        ActorDTO actorDTO = null;

        if (byIdOptional.isPresent()) {
            Actor actorById = byIdOptional.get();
            actorDTO = actorCycleMapper.toDto(actorById, context);
        } else {
            logicExceptionComponent.throwExceptionEntityNotFound("Actor", id);
        }

        return actorDTO;
    }

    @Override
    public ActorDTO save(ActorDTO dto) {
        Actor actorToSave = actorCycleMapper.toEntity(dto, context);
        Actor actorSaved = actorRepository.save(actorToSave);
        ActorDTO actorDtoSaved = actorCycleMapper.toDto(actorSaved, context);
        return actorDtoSaved;
    }

    public ActorDTO updateActor(ActorDTO actorDtoToUpdate, Long id) {
        Optional<Actor> byIdOptional = actorRepository.findById(id);
        ActorDTO actorDtoUpdated = null;

        if (byIdOptional.isPresent()) {
            Actor actorById = byIdOptional.get();
            actorDtoToUpdate.setId(actorById.getId());
            Actor actorToUpdate = actorCycleMapper.toEntity(actorDtoToUpdate, context);
            Actor actorUpdated = actorRepository.save(actorToUpdate);
            actorDtoUpdated = actorCycleMapper.toDto(actorUpdated, context);
        } else {
            logicExceptionComponent.throwExceptionEntityNotFound("Actor", id);
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
            logicExceptionComponent.throwExceptionEntityNotFound("Actor", id);
        }
    }

}