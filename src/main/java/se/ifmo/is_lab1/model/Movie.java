package se.ifmo.is_lab1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import se.ifmo.is_lab1.model.enums.MovieGenre;
import se.ifmo.is_lab1.model.enums.MpaaRating;

import java.io.Serializable;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
public class Movie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @Column(nullable = false)
    private String name; //Поле не может быть null, Строка не может быть пустой

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Coordinates coordinates; //Поле не может быть null

    @Column(nullable = false)
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @Column(nullable = false)
    private Long oscarsCount; //Значение поля должно быть больше 0, Поле не может быть null

    @Column
    private long budget; //Значение поля должно быть больше 0

    @Column
    private int totalBoxOffice; //Значение поля должно быть больше 0

    @Column
    private MpaaRating mpaaRating; //Поле может быть null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Person director; //Поле может быть null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Person screenwriter;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Person operator; //Поле может быть null

    @Column
    private int length; //Значение поля должно быть больше 0

    @Column
    private Long goldenPalmCount; //Значение поля должно быть больше 0, Поле может быть null

    @Column(nullable = false)
    private Long usaBoxOffice; //Поле не может быть null, Значение поля должно быть больше 0

    @Column(nullable = false)
    private String tagline; //Длина строки не должна быть больше 143, Поле не может быть null

    @Column
    private MovieGenre genre; //Поле может быть null

//    @Column
//    @NotNull
//    private Boolean isEditable = Boolean.FALSE;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private User user;

}
