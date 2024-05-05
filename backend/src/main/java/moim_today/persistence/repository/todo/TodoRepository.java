package moim_today.persistence.repository.todo;

import moim_today.persistence.entity.todo.TodoJpaEntity;

import java.util.List;

public interface TodoRepository {
    void deleteAllByMoimId(final long moimId);

    void save(final TodoJpaEntity todoJpaEntity);

    long count();

    void deleteAllTodosCreatedByMemberInMoim(final long moimId, final long memberId);

    List<TodoJpaEntity> getAllTodosByMemberId(final long memberId);
}
