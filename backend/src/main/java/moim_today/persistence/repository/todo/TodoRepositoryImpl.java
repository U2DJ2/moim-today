package moim_today.persistence.repository.todo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import moim_today.dto.todo.QTodoResponse;
import moim_today.dto.todo.TodoResponse;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.todo.TodoJpaEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static moim_today.global.constant.exception.TodoExceptionConstant.TODO_NOT_FOUND_ERROR;
import static moim_today.persistence.entity.todo.QTodoJpaEntity.todoJpaEntity;

@Repository
public class TodoRepositoryImpl implements TodoRepository {

    private final TodoJpaRepository todoJpaRepository;
    private final JPAQueryFactory queryFactory;

    public TodoRepositoryImpl(final TodoJpaRepository todoJpaRepository, final JPAQueryFactory queryFactory) {
        this.todoJpaRepository = todoJpaRepository;
        this.queryFactory = queryFactory;
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
        todoJpaRepository.deleteAllByMoimIdAndMemberId(moimId, memberId);
    }

    @Override
    public List<TodoJpaEntity> getAllByMemberId(final long memberId) {
        return todoJpaRepository.findAllByMemberId(memberId);
    }

    @Override
    public List<TodoResponse> findAllByDateRange(final long memberId, final long moimId,
                                                 final LocalDate startDate, final LocalDate endDate) {
                return queryFactory.select(
                new QTodoResponse(
                        todoJpaEntity.id,
                        todoJpaEntity.contents,
                        todoJpaEntity.todoProgress,
                        todoJpaEntity.todoDate
                ))
                .from(todoJpaEntity)
                .where(
                        todoJpaEntity.memberId.eq(memberId)
                                .and(todoJpaEntity.moimId.eq(moimId))
                                .and(todoJpaEntity.todoDate.between(startDate, endDate))
                )
                .fetch();
    }

    @Override
    public TodoJpaEntity getById(final long todoId) {
        return todoJpaRepository.findById(todoId)
                .orElseThrow(() -> new NotFoundException(TODO_NOT_FOUND_ERROR.message()));
    }

    //
    @Override
    public void deleteById(final long todoId) {
        todoJpaRepository.deleteById(todoId);
    }
}
