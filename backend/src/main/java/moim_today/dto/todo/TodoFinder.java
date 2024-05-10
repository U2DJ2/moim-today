package moim_today.dto.todo;

import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.todo.TodoJpaEntity;
import moim_today.persistence.repository.todo.TodoRepository;

import java.util.List;

@Implement
public class TodoFinder {

    private final TodoRepository todoRepository;

    public TodoFinder(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<TodoResponse> findAll(final long memberId, final long moimId) {
        List<TodoJpaEntity> findTodos = todoRepository.getAllByMemberIdAndMoimId(memberId, moimId);
        return findTodos.stream().map(TodoResponse::of).toList();
    }
}
