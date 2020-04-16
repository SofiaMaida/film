package ar.com.ada.sb.api.film.service;

import ar.com.ada.sb.api.film.exception.ApiEntityError;
import ar.com.ada.sb.api.film.exception.BusinessLogicException;
import ar.com.ada.sb.api.film.model.dto.FilmDTO;
import ar.com.ada.sb.api.film.model.entity.Film;
import ar.com.ada.sb.api.film.model.mapper.FilmMapper;
import ar.com.ada.sb.api.film.model.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("filmServices")
public class FilmServices implements Services<FilmDTO> {

    @Autowired @Qualifier("filmRepository")
    private FilmRepository filmRepository;

    private FilmMapper filmMapper;

    public FilmServices(FilmMapper filmMapper) {
        this.filmMapper = filmMapper;
    }

    @Override
    public List<FilmDTO> findAll() {
        List<Film> filmsEntityList = filmRepository.findAll();
        List<FilmDTO> filmDTOList = filmMapper.toDto(filmsEntityList);
        return filmDTOList;
    }

    public FilmDTO findFilmById(Long id) {
        Optional<Film> byIdOptional = filmRepository.findById(id);
        FilmDTO filmDTO = null;

        if (byIdOptional.isPresent()) {
            Film filmById = byIdOptional.get();
            filmDTO = filmMapper.toDto(filmById);
        } else {
            throwBusinessLogicException(id);
        }
        return filmDTO;
    }

    @Override
    public FilmDTO save(FilmDTO dto) {
        Film filmToSave = filmMapper.toEntity(dto);
        Film filmSaved = filmRepository.save(filmToSave);
        FilmDTO filmDTOSaved = filmMapper.toDto(filmSaved);
        return filmDTOSaved;
    }

    public FilmDTO updateFilm(FilmDTO filmDTOToUpdate, Long id) {
        Optional<Film> byIdOptional = filmRepository.findById(id);
        FilmDTO filmDTOUpdated = null;

        if (byIdOptional.isPresent()) {
            Film filmById = byIdOptional.get();
            filmDTOToUpdate.setId(filmById.getId());
            Film filmToUpdate = filmMapper.toEntity(filmDTOToUpdate);
            Film filmUpdated = filmRepository.save(filmToUpdate);
            filmDTOToUpdate = filmMapper.toDto(filmUpdated);
        } else {
            throwBusinessLogicException(id);
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
            throwBusinessLogicException(id);
        }
    }

    private void throwBusinessLogicException(Long id) {
        ApiEntityError apiEntityError = new ApiEntityError(
                "Film",
                "NotFound",
                "The Film with id '" + id + "' does not exist"
        );

        throw new BusinessLogicException(
                "film does not exist",
                HttpStatus.NOT_FOUND,
                apiEntityError
        );
    }

}
