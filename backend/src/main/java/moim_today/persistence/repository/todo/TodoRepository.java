package moim_today.persistence.repository.todo;

import moim_today.persistence.entity.todo.TodoJpaEntity;

import java.util.List;

public interface TodoRepository {
    void deleteAllByMoimId(final long moimId);

    TodoJpaEntity save(final TodoJpaEntity todoJpaEntity);

    long count();

    void deleteAllCreatedByMemberInMoim(final long memberId, final long moimId);

    List<TodoJpaEntity> getAllByMemberId(final long memberId);

    List<TodoJpaEntity> getAllByMemberIdAndMoimId(final long memberId, final long moimId);
}
