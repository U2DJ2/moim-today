package moim_today.implement.todo;

import moim_today.dto.todo.TodoUpdateRequest;
import moim_today.dto.todo.TodoUpdateResponse;
import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.todo.TodoJpaEntity;

@Implement
public class TodoUpdater {

    public TodoUpdateResponse updateTodo(final TodoJpaEntity originalTodo,
                                         final TodoUpdateRequest todoUpdateRequest) {
        originalTodo.updateTodo(todoUpdateRequest);
        return TodoUpdateResponse.from(originalTodo);
    }
}
