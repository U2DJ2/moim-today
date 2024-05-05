package moim_today.persistence.repository.todo;

import moim_today.persistence.entity.todo.TodoJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TodoRepositoryImpl implements TodoRepository {

    private final TodoJpaRepository todoJpaRepository;

    public TodoRepositoryImpl(final TodoJpaRepository todoJpaRepository) {
        this.todoJpaRepository = todoJpaRepository;
    }

    @Override
    public void deleteAllByMoimId(final long moimId) {
        todoJpaRepository.deleteAllByMoimId(moimId);
    }

    @Override
    public void save(final TodoJpaEntity todoJpaEntity) {
        todoJpaRepository.save(todoJpaEntity);
    }

    @Override
    public long count() {
        return todoJpaRepository.count();
    }

    @Override
    public void deleteAllTodosCreatedByMemberInMoim(final long moimId, final long memberId) {
        todoJpaRepository.deleteAllByMoimIdAndMemberId(moimId,memberId);
    }

    @Override
    public List<TodoJpaEntity> getAllTodosByMemberId(final long memberId) {
        return todoJpaRepository.findAllByMemberId(memberId);
    }
}
