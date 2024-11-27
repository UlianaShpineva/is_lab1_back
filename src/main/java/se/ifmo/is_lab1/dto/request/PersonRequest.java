package se.ifmo.is_lab1.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import se.ifmo.is_lab1.model.Location;
import se.ifmo.is_lab1.model.enums.Color;
import se.ifmo.is_lab1.model.enums.Country;

@Data
public class PersonRequest {
    @NotNull
    @NotBlank
    @NotEmpty
    private String name; //Поле не может быть null, Строка не может быть пустой

    @NotNull
    private Color eyeColor; //Поле не может быть null

    private Color hairColor; //Поле может быть null

    private Long locationId; //Поле может быть null

    @NotNull
    private Long weight; //Поле не может быть null

    @NotNull
    private Country nationality; //Поле не может быть null
}
