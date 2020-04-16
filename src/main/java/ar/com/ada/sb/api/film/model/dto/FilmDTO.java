package ar.com.ada.sb.api.film.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor
@JsonPropertyOrder({"id", "title", "gender", "year" ,"description"})
public class FilmDTO implements Serializable {

    private Long id;

    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "gender is required")
    private String gender;

    @JsonFormat(pattern = "yyyy")
    @NotNull(message = "year is requeride")
    @PastOrPresent(message = "the year must be past or present")
    private Date year;

    @NotNull(message = "description is required")
    private String description;

    private Set<ActorDTO> actors;

    public FilmDTO(Long id, String title, String gender, Date year, String description, Set<ActorDTO> actors) {
        this.id = id;
        this.title = title;
        this.gender = gender;
        this.year = year;
        this.description = description;
        this.actors = actors;
    }

    public FilmDTO(String title, String gender, Date year, String description, Set<ActorDTO> actors) {
        this.title = title;
        this.gender = gender;
        this.year = year;
        this.description = description;
        this.actors = actors;
    }

    @Override
    public String toString() {
        return "FilmDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", gender='" + gender + '\'' +
                ", year=" + year +
                ", description='" + description + '\'' +
                ", actors=" + actors +
                '}';
    }
}
