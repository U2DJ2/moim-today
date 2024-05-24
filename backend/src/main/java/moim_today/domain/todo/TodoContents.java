package moim_today.domain.todo;

import moim_today.domain.todo.enums.TodoProgress;

public record TodoContents(
        long todoId,
        String contents,
        TodoProgress todoProgress
) {
}
