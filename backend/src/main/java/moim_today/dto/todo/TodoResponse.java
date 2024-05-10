package moim_today.dto.todo;

import lombok.Builder;
import moim_today.persistence.entity.todo.TodoJpaEntity;

@Builder
public record TodoResponse(

) {
    public static TodoResponse of(final TodoJpaEntity t) {
        return TodoResponse.builder()
                .build();
    }
}
