package se.ifmo.is_lab1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import se.ifmo.is_lab1.model.enums.Color;
import se.ifmo.is_lab1.model.enums.Country;

import java.io.Serializable;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name; //Поле не может быть null, Строка не может быть пустой

    @Column(nullable = false)
    private Color eyeColor; //Поле не может быть null

    @Column
    private Color hairColor; //Поле может быть null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Location location; //Поле может быть null

    @Column(nullable = false)
    private Long weight; //Поле не может быть null

    @Column(nullable = false)
    private Country nationality; //Поле не может быть null

    @OneToMany(mappedBy = "director")
    @JsonIgnore
    private List<Movie> movies;
}
