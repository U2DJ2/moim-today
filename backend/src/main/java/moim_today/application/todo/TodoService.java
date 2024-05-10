package moim_today.application.todo;

import moim_today.dto.todo.TodoCreateRequest;
import moim_today.dto.todo.TodoResponse;

import java.util.List;

public interface TodoService {

    void createTodo(final long memberId, final TodoCreateRequest todoCreateRequest);

    List<TodoResponse> findAll(final long memberId, long moimId);
}
