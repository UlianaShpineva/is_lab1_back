package se.ifmo.is_lab1.model;

import jakarta.persistence.*;
import lombok.*;
import se.ifmo.is_lab1.model.enums.OperationType;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "history_list")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false, name = "movies_id")
    private int movieId;

    @Column(nullable = false, name = "ops_type")
    private OperationType operationType;

    @Column(nullable = false, name = "users_id")
    private Long userId;

    @Column(nullable = false)
    private java.util.Date timeOp;
}
