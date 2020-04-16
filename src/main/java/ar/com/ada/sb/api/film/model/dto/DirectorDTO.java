package ar.com.ada.sb.api.film.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor
@JsonPropertyOrder({"id", "name", "last_name", "birthday", "biography"})
public class DirectorDTO implements Serializable {

    private Long id;

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "last_name is required")
    private String last_name;

    @NotNull(message = "birthday is required")
    @Past(message = "the birthday must be past date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    @NotNull(message = "biography is required")
    private String biography;

    private Set<FilmDTO> films;

    public DirectorDTO(Long id, String name, String last_name, Date birthday, String biography, Set<FilmDTO> films) {
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.birthday = birthday;
        this.biography = biography;
        this.films = films;
    }

    public DirectorDTO(String name, String last_name, Date birthday, String biography, Set<FilmDTO> films) {
        this.name = name;
        this.last_name = last_name;
        this.birthday = birthday;
        this.biography = biography;
        this.films = films;
    }

    @Override
    public String toString() {
        return "DirectorDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", birthday=" + birthday +
                ", biography='" + biography + '\'' +
                ", films='" + films + '\'' +
                '}';
    }
}
