package ar.com.ada.sb.api.film.service;

import ar.com.ada.sb.api.film.exception.ApiEntityError;
import ar.com.ada.sb.api.film.exception.BusinessLogicException;
import ar.com.ada.sb.api.film.model.dto.DirectorDTO;
import ar.com.ada.sb.api.film.model.entity.Director;
import ar.com.ada.sb.api.film.model.mapper.DirectorMapper;
import ar.com.ada.sb.api.film.model.repository.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("directorServices")
public class DirectorServices implements Services<DirectorDTO>{

    @Autowired @Qualifier("directorRepository")
    private DirectorRepository directorRepository;

    private DirectorMapper directorMapper;

    public DirectorServices(DirectorMapper directorMapper) {
        this.directorMapper = directorMapper;
    }

    @Override
    public List<DirectorDTO> findAll() {
        List<Director> directorsEntityList = directorRepository.findAll();
        List<DirectorDTO> directorsDtoList = directorMapper.toDto(directorsEntityList);
        return directorsDtoList;
    }

    public DirectorDTO findDirectorById(Long id) {
        // SELECT * FROM Director WHERE id = ?
        Optional<Director> byIdOptional = directorRepository.findById(id);
        DirectorDTO directorDTO = null;

        if (byIdOptional.isPresent()) {
            Director directorById = byIdOptional.get();
            directorDTO = directorMapper.toDto(directorById);
        } else {
            throwBusinessLogicException(id);
        }
        return directorDTO;
    }

    @Override
    public DirectorDTO save(DirectorDTO dto) {
        Director directorToSave = directorMapper.toEntity(dto);
        Director directorSaved = directorRepository.save(directorToSave);
        DirectorDTO directorDTOSaved = directorMapper.toDto(directorSaved);
        return directorDTOSaved;
    }

    public DirectorDTO updateDirector(DirectorDTO directorDTOToUpdate, Long id) {
        Optional<Director> byIdOptional = directorRepository.findById(id);
        DirectorDTO directorDTOUpdated = null;

        if (byIdOptional.isPresent()) {
            Director directorById = byIdOptional.get();
            directorDTOToUpdate.setId(directorById.getId());
            Director directorToUpdate = directorMapper.toEntity(directorDTOToUpdate);
            Director directorUpdated = directorRepository.save(directorToUpdate);
            directorDTOUpdated = directorMapper.toDto(directorUpdated);
        } else {
            throwBusinessLogicException(id);
        }
        return directorDTOUpdated;
    }

    @Override
    public void delete(Long id) {
        Optional<Director> byIdOptional = directorRepository.findById(id);

        if (byIdOptional.isPresent()) {
            Director directorToDelete = byIdOptional.get();
            directorRepository.delete(directorToDelete);
        } else {
            throwBusinessLogicException(id);
        }
    }

    private void throwBusinessLogicException(Long id) {
        ApiEntityError apiEntityError = new ApiEntityError(
                "Director",
                "NotFound",
                "The Director with id '" + id + "' does not exist"
        );

        throw new BusinessLogicException(
                "Director does not exist",
                HttpStatus.NOT_FOUND,
                apiEntityError
        );
    }

}
