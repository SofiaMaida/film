package ar.com.ada.sb.api.film.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor
@Entity(name = "Actor")
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 20)
    private String gender;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private LocalDate birthday;

    @ManyToMany(mappedBy = "actors")
    private Set<Film> films;

    public Actor(Long id) {
        this.id = id;
    }

}
