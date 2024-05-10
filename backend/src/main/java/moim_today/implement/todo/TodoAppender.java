package moim_today.implement.todo;

import moim_today.dto.todo.TodoCreateRequest;
import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.todo.TodoJpaEntity;
import moim_today.persistence.repository.todo.TodoRepository;

@Implement
public class TodoAppender {

    private final TodoRepository todoRepository;

    public TodoAppender(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public TodoJpaEntity createTodo(final long memberId, final TodoCreateRequest todoCreateRequest){
        TodoJpaEntity todoJpaEntity = todoCreateRequest.toEntity(memberId);
        return todoRepository.save(todoJpaEntity);
    }
}
