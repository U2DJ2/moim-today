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
    public TodoJpaEntity save(final TodoJpaEntity todoJpaEntity) {
        return todoJpaRepository.save(todoJpaEntity);
    }

    @Override
    public long count() {
        return todoJpaRepository.count();
    }

    @Override
    public void deleteAllCreatedByMemberInMoim(final long memberId, final long moimId) {
        todoJpaRepository.deleteAllByMoimIdAndMemberId(moimId,memberId);
    }

    @Override
    public List<TodoJpaEntity> getAllByMemberId(final long memberId) {
        return todoJpaRepository.findAllByMemberId(memberId);
    }

    @Override
    public List<TodoJpaEntity> getAllByMemberIdAndMoimId(final long memberId, final long moimId) {
        return todoJpaRepository.findAllByMemberIdAndMoimId(memberId, moimId);
    }
}
