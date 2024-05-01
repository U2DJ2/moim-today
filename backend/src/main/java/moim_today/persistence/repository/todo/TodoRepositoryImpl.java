package moim_today.persistence.repository.todo;

import org.springframework.stereotype.Repository;

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
}
