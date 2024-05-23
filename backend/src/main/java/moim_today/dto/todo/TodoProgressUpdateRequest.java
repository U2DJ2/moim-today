package moim_today.dto.todo;

import moim_today.domain.todo.enums.TodoProgress;

public record TodoProgressUpdateRequest(
        long todoId,
        TodoProgress todoProgress
) {
}
