package moim_today.implement.todo;

import moim_today.global.annotation.Implement;
import moim_today.persistence.repository.todo.TodoRepository;
import org.springframework.transaction.annotation.Transactional;

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
}
