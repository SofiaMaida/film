package ar.com.ada.sb.api.film.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor
@JsonPropertyOrder({"id", "name", "gender", "birthday"})
public class ActorDTO implements Serializable {

    private Long id;

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "gender is required")
    private String gender;

    @NotNull(message = "birthday is required")
    @Past(message = "the birthday must be past date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @JsonIgnoreProperties(value = "actors")
    private Set<FilmDTO> films;

    public ActorDTO(Long id, String name, String gender, LocalDate birthday, Set<FilmDTO> films) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.films = films;
    }

    public ActorDTO(String name, String gender, LocalDate birthday, Set<FilmDTO> films) {
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.films = films;
    }
}