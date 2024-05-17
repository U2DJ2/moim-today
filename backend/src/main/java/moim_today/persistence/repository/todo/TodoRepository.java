package moim_today.persistence.repository.todo;

import moim_today.dto.todo.TodoResponse;
import moim_today.persistence.entity.todo.TodoJpaEntity;

import java.time.LocalDate;
import java.util.List;

public interface TodoRepository {
    void deleteAllByMoimId(final long moimId);

    TodoJpaEntity save(final TodoJpaEntity todoJpaEntity);

    long count();

    void deleteAllCreatedByMemberInMoim(final long memberId, final long moimId);

    List<TodoJpaEntity> getAllByMemberId(final long memberId);

    List<TodoResponse> findAllByDateRange(final long memberId, final long moimId,
                                          final LocalDate startDate, final LocalDate endDate);

    TodoJpaEntity getById(final long todoId);

    void deleteById(final long todoId);
}
