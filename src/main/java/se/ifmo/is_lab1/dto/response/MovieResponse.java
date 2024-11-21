package se.ifmo.is_lab1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import se.ifmo.is_lab1.model.Coordinates;
import se.ifmo.is_lab1.model.Person;
import se.ifmo.is_lab1.model.enums.MovieGenre;
import se.ifmo.is_lab1.model.enums.MpaaRating;

@Data
@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
public class MovieResponse {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long oscarsCount; //Значение поля должно быть больше 0, Поле не может быть null
    private long budget; //Значение поля должно быть больше 0
    private int totalBoxOffice; //Значение поля должно быть больше 0
    private MpaaRating mpaaRating; //Поле может быть null
    private Person director; //Поле может быть null
    private Person screenwriter;
    private Person operator; //Поле может быть null
    private int length; //Значение поля должно быть больше 0
    private Long goldenPalmCount; //Значение поля должно быть больше 0, Поле может быть null
    private Long usaBoxOffice; //Поле не может быть null, Значение поля должно быть больше 0
    private String tagline; //Длина строки не должна быть больше 143, Поле не может быть null
    private MovieGenre genre; //Поле может быть null
    private UserResponse user;
}
