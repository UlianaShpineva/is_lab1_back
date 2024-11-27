package se.ifmo.is_lab1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Optional;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private Long x; //Поле не может быть null

    @Column
    private double y;

    @Column(nullable = false)
    private Long z; //Поле не может быть null

    @Column(nullable = false)
    private String name; //Длина строки не должна быть больше 392, Поле не может быть null

    @OneToMany(mappedBy = "location")
    @JsonIgnore
    private List<Person> persons;
}
