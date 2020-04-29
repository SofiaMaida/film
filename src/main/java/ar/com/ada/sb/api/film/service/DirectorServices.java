package ar.com.ada.sb.api.film.service;

import ar.com.ada.sb.api.film.component.BusinessLogicExceptionComponent;
import ar.com.ada.sb.api.film.model.dto.DirectorDTO;
import ar.com.ada.sb.api.film.model.entity.Director;
import ar.com.ada.sb.api.film.model.mapper.circular.dependency.CycleAvoidingMappingContext;
import ar.com.ada.sb.api.film.model.mapper.circular.dependency.DirectorCycleMapper;
import ar.com.ada.sb.api.film.model.repository.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("directorServices")
public class DirectorServices implements Services<DirectorDTO>{

    @Autowired @Qualifier("businessLogicExceptionComponent")
    private BusinessLogicExceptionComponent logicExceptionComponent;

    @Autowired @Qualifier("directorRepository")
    private DirectorRepository directorRepository;

    private DirectorCycleMapper directorCycleMapper = DirectorCycleMapper.MAPPER;

    @Autowired @Qualifier("cycleAvoidingMappingContext")
    private CycleAvoidingMappingContext context;

    @Override
    public List<DirectorDTO> findAll() {
        List<Director> all = directorRepository.findAll();
        List<DirectorDTO> directorDtoList = directorCycleMapper.toDto(all, context);
        return directorDtoList;
    }

    public DirectorDTO findDirectorById(Long id) {
        Optional<Director> byIdOptional = directorRepository.findById(id);
        DirectorDTO directorDTO = null;

        if (byIdOptional.isPresent()) {
            Director director = byIdOptional.get();
            directorDTO = directorCycleMapper.toDto(director, context);
        } else {
            logicExceptionComponent.throwExceptionEntityNotFound("Director", id);
        }
        return directorDTO;
    }

    @Override
    public DirectorDTO save(DirectorDTO dto) {
        Director directorToSave = directorCycleMapper.toEntity(dto, context);
        Director directorSaved = directorRepository.save(directorToSave);
        DirectorDTO directorDtoSaved = directorCycleMapper.toDto(directorSaved, context);
        return directorDtoSaved;
    }

    public DirectorDTO updateDirector(DirectorDTO directorDtoToUpdate, Long id) {
        Optional<Director> byIdOptional = directorRepository.findById(id);
        DirectorDTO directorDtoUpdated = null;

        if (byIdOptional.isPresent()) {
            Director directorById = byIdOptional.get();
            directorDtoToUpdate.setId(directorById.getId());
            Director directorToUpdate = directorCycleMapper.toEntity(directorDtoToUpdate, context);
            Director directorUpdated = directorRepository.save(directorToUpdate);
            directorDtoUpdated = directorCycleMapper.toDto(directorUpdated, context);
        } else {
            logicExceptionComponent.throwExceptionEntityNotFound("Director", id);
        }

        return directorDtoUpdated;
    }

    @Override
    public void delete(Long id) {
        Optional<Director> byIdOptional = directorRepository.findById(id);

        if (byIdOptional.isPresent()) {
            Director directorToDelete = byIdOptional.get();
            directorRepository.delete(directorToDelete);
        } else {
            logicExceptionComponent.throwExceptionEntityNotFound("Director", id);
        }
    }

}
