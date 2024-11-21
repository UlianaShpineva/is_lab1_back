package se.ifmo.is_lab1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
public class LocationResponse {
    private Long id;
    private Long x; //Поле не может быть null
    private double y;
    private Long z; //Поле не может быть null
    private String name; //Длина строки не должна быть больше 392, Поле не может быть null
}
