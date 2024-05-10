package moim_today.dto.todo;

import lombok.Builder;
import moim_today.domain.todo.enums.TodoProgress;
import moim_today.persistence.entity.todo.TodoJpaEntity;

@Builder
public record TodoResponse(
        long todoId,
        long memberId,
        String contents,
        TodoProgress todoProgress

) {
    public static TodoResponse of(final TodoJpaEntity t) {
        return TodoResponse.builder()
                .build();
    }
}
