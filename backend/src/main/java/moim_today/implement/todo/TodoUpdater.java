package moim_today.implement.todo;

import moim_today.domain.todo.enums.TodoProgress;
import moim_today.dto.todo.TodoUpdateRequest;
import moim_today.dto.todo.TodoUpdateResponse;
import moim_today.global.annotation.Implement;
import moim_today.global.error.ForbiddenException;
import moim_today.persistence.entity.todo.TodoJpaEntity;
import moim_today.persistence.repository.todo.TodoRepository;
import org.springframework.transaction.annotation.Transactional;

import static moim_today.global.constant.exception.TodoExceptionConstant.TODO_NOT_OWNER_ERROR;

@Implement
public class TodoUpdater {

    private final TodoRepository todoRepository;

    public TodoUpdater(final TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Transactional
    public TodoUpdateResponse updateTodo(final long memberId, final TodoUpdateRequest todoUpdateRequest) {
        TodoJpaEntity originalTodo = todoRepository.getById(todoUpdateRequest.todoId());
        validateTodoOwner(memberId, originalTodo);
        originalTodo.updateTodo(todoUpdateRequest);
        return TodoUpdateResponse.from(originalTodo);
    }

    @Transactional
    public TodoUpdateResponse updateTodoProgress(final long memberId, final long todoId, final TodoProgress todoProgress) {
        TodoJpaEntity findTodo = todoRepository.getById(todoId);
        validateTodoOwner(memberId, findTodo);
        findTodo.updateTodoProgress(todoProgress);
        return TodoUpdateResponse.from(findTodo);
    }

    private void validateTodoOwner(final long memberId, final TodoJpaEntity todoJpaEntity) {
        if (!isTodoOwner(memberId, todoJpaEntity)) {
            throw new ForbiddenException(TODO_NOT_OWNER_ERROR.message());
        }
    }

    private boolean isTodoOwner(final long memberId, final TodoJpaEntity todoJpaEntity) {
        return todoJpaEntity.getMemberId() == memberId;
    }
}
