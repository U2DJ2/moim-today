package moim_today.persistence.entity.todo;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import moim_today.domain.todo.enums.TodoProgress;
import moim_today.dto.todo.TodoUpdateRequest;
import moim_today.global.annotation.Association;
import moim_today.global.base_entity.BaseTimeEntity;

import java.time.LocalDate;

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

    private LocalDate todoDate;

    @Builder
    public TodoJpaEntity(final long moimId, final long memberId, final String contents, final TodoProgress todoProgress, final LocalDate todoDate) {
        this.moimId = moimId;
        this.memberId = memberId;
        this.contents = contents;
        this.todoProgress = todoProgress;
        this.todoDate = todoDate;
    }

    protected TodoJpaEntity() {
    }

    public void updateTodo(final TodoUpdateRequest todoUpdateRequest) {
        this.todoProgress = todoUpdateRequest.todoProgress();
        this.contents = todoUpdateRequest.contents();
        this.todoDate = todoUpdateRequest.todoDate();
    }

    public void updateTodoProgress(final TodoProgress todoProgress) {
        this.todoProgress = todoProgress;
    }
}
