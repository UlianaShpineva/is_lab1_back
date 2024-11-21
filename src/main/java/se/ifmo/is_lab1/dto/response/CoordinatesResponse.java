package se.ifmo.is_lab1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
public class CoordinatesResponse {
    private Long id;
    private Float x; //Максимальное значение поля: 953, Поле не может быть null
    private float y; //Значение поля должно быть больше -955
}
