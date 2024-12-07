package se.ifmo.is_lab1.dto.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import se.ifmo.is_lab1.model.enums.OperationType;

@Data
@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
public class HistoryResponse {
    private int movieId;
    private OperationType operationType;
    private Long userId;
    private java.util.Date timeOp;
}
