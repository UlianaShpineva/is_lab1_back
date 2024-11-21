package se.ifmo.is_lab1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
public class Coordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private Float x; //Максимальное значение поля: 953, Поле не может быть null

    @Column(nullable = false    )
    private float y; //Значение поля должно быть больше -955

    @OneToMany(mappedBy = "coordinates")
    @JsonIgnore
    private List<Movie> movies;
}