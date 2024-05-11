package moim_today.implement.todo;

import moim_today.dto.todo.TodoResponse;
import moim_today.global.annotation.Implement;
import moim_today.persistence.repository.todo.TodoRepository;

import java.time.LocalDateTime;
import java.util.List;

@Implement
public class TodoFinder {

    private final TodoRepository todoRepository;

    public TodoFinder(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<TodoResponse> findAllByDateRange(final long memberId,
                                                 final long moimId,
                                                 final LocalDateTime startDateTime,
                                                 final LocalDateTime endDateTime) {
        return todoRepository.findAllByDateRange(memberId, moimId, startDateTime, endDateTime);
    }
}
