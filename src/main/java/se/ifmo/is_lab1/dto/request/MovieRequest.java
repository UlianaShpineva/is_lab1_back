package se.ifmo.is_lab1.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import se.ifmo.is_lab1.model.Person;
import se.ifmo.is_lab1.model.enums.MovieGenre;
import se.ifmo.is_lab1.model.enums.MpaaRating;

@Data
public class MovieRequest {
    @NotNull
    @NotBlank
    @NotEmpty
    private String name; //Поле не может быть null, Строка не может быть пустой

    @NotNull
    private Long coordinatesId; //Поле не может быть null

    @NotNull
    @Positive
    private Long oscarsCount; //Значение поля должно быть больше 0, Поле не может быть null

    @NotNull
    @Positive
    private long budget; //Значение поля должно быть больше 0

    @NotNull
    @Positive
    private int totalBoxOffice; //Значение поля должно быть больше 0

    @NotNull
    private MpaaRating mpaaRating; //Поле может быть null


    @NotNull
    private Long directorId; //Поле может быть null

    @NotNull
    private Long screenwriterId;

    @NotNull
    private Long operatorId; //Поле может быть null //Person

    @NotNull
    @Positive
    private int length; //Значение поля должно быть больше 0

    @NotNull
    @Positive
    private Long goldenPalmCount; //Значение поля должно быть больше 0, Поле может быть null

    @NotNull
    @Positive
    private Long usaBoxOffice; //Поле не может быть null, Значение поля должно быть больше 0

    @NotNull
    @Length(max = 143)
    private String tagline; //Длина строки не должна быть больше 143, Поле не может быть null

    @NotNull
    private MovieGenre genre; //Поле может быть null

    @NotNull
    private Boolean isEditable;// = Boolean.TRUE;
}
