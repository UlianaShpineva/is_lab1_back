package se.ifmo.is_lab1.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CoordinatesRequest {
    @NotNull
    @Max(953)
    private Float x; //Максимальное значение поля: 953, Поле не может быть null

    @NotNull
    @Min(-954)
    private float y; //Значение поля должно быть больше -955
}
