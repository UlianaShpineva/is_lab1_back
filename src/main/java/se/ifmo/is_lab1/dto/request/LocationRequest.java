package se.ifmo.is_lab1.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class LocationRequest {
    @NotNull
    private Long x; //Поле не может быть null

    private double y;

    @NotNull
    private Long z; //Поле не может быть null

    @NotNull
    @Length(max = 392)
    private String name; //Длина строки не должна быть больше 392, Поле не может быть null
}
