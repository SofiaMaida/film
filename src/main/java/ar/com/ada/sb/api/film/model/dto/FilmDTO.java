package ar.com.ada.sb.api.film.model.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor
public class FilmDTO implements Serializable {

    private Long id;

    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "description is required")
    private String description;

    @JsonIgnoreProperties(value = "films")
    private DirectorDTO director;

    @JsonIgnoreProperties(value = "films")
    private Set<ActorDTO> actors;
}

