package moim_today.application.todo;

import moim_today.dto.todo.TodoCreateRequest;

public interface TodoService {

    void createTodo(final long memberId, final TodoCreateRequest todoCreateRequest);
}
