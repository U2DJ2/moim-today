package moim_today.implement.todo;

import moim_today.dto.todo.TodoRemoveRequest;
import moim_today.global.annotation.Implement;
import moim_today.global.error.ForbiddenException;
import moim_today.persistence.entity.todo.TodoJpaEntity;
import moim_today.persistence.repository.todo.TodoRepository;
import org.springframework.transaction.annotation.Transactional;

import static moim_today.global.constant.exception.TodoExceptionConstant.TODO_NOT_OWNER_ERROR;

@Implement
public class TodoRemover {

    private final TodoRepository todoRepository;

    public TodoRemover(final TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Transactional
    public void deleteAllByMoimId(final long moimId) {
        todoRepository.deleteAllByMoimId(moimId);
    }

    @Transactional
    public void deleteAllTodosCreatedByMemberInMoim(final long moimId, final long memberId) {
        todoRepository.deleteAllCreatedByMemberInMoim(memberId, moimId);
    }

    @Transactional
    public void deleteById(final long todoId) {
        todoRepository.deleteById(todoId);
    }

    @Transactional
    public void deleteTodo(final long memberId, final TodoRemoveRequest todoRemoveRequest) {
        long deleteTodoId = todoRemoveRequest.todoId();
        TodoJpaEntity originalTodo = todoRepository.getById(deleteTodoId);
        validateTodoOwner(memberId, originalTodo);
        todoRepository.deleteById(deleteTodoId);
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
