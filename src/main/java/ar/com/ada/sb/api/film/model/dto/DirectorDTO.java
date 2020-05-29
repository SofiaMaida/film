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
    private String lastName;

    @NotNull(message = "birthday is required")
    @Past(message = "the birthday must be past date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    @NotBlank(message = "biography is required")
    private String biography;

    @JsonIgnoreProperties(value = "director")
    private Set<FilmDTO> films;

}