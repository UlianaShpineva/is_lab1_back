package se.ifmo.is_lab1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import se.ifmo.is_lab1.model.Location;
import se.ifmo.is_lab1.model.enums.Color;
import se.ifmo.is_lab1.model.enums.Country;

@Data
@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
public class PersonResponse {
    private Long id;
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Color eyeColor; //Поле не может быть null
    private Color hairColor; //Поле может быть null
    private Location location; //Поле может быть null
    private Long weight; //Поле не может быть null
    private Country nationality; //Поле не может быть null
}
