package moim_today.persistence.entity.todo;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import moim_today.domain.todo.enums.TodoProgress;
import moim_today.global.annotation.Association;
import moim_today.global.base_entity.BaseTimeEntity;

import java.time.LocalDateTime;

@Getter
@Table(name = "todo")
@Entity
public class TodoJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private long id;

    @Association
    private long moimId;

    @Association
    private long memberId;

    private String contents;

    @Enumerated(EnumType.STRING)
    private TodoProgress todoProgress;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    protected TodoJpaEntity() {
    }

    @Builder
    private TodoJpaEntity(final long moimId, final long memberId, final String contents,
                          final TodoProgress todoProgress, final LocalDateTime startDateTime,
                          final LocalDateTime endDateTime) {
        this.moimId = moimId;
        this.memberId = memberId;
        this.contents = contents;
        this.todoProgress = todoProgress;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }
}
