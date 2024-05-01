package moim_today.implement.todo;

import moim_today.global.annotation.Implement;
import moim_today.persistence.repository.todo.TodoRepository;

@Implement
public class TodoRemover {

    private final TodoRepository todoRepository;

    public TodoRemover(final TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public void deleteAllByMoimId(final long moimId) {
        todoRepository.deleteAllByMoimId(moimId);
    }
}
