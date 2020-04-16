package ar.com.ada.sb.api.film.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor
@Entity(name = "Director")
public class Director {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String last_name;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private Date birthday;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String biography;

    @OneToMany(mappedBy = "directors")
    private Set<Film> films;

    public Director(Long id) {
        this.id = id;
    }
}
