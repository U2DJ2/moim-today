package moim_today.implement.todo;

import moim_today.domain.todo.enums.TodoProgress;
import moim_today.dto.todo.*;
import moim_today.global.annotation.Implement;
import moim_today.global.error.ForbiddenException;
import moim_today.implement.moim.joined_moim.JoinedMoimFinder;
import moim_today.persistence.entity.todo.TodoJpaEntity;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static moim_today.global.constant.TimeConstant.MONTH_START_POINT;
import static moim_today.global.constant.exception.TodoExceptionConstant.TODO_NOT_OWNER_ERROR;

@Implement
public class TodoManager {

    private final TodoFinder todoFinder;
    private final TodoUpdater todoUpdater;
    private final TodoRemover todoRemover;
    private final JoinedMoimFinder joinedMoimFinder;

    public TodoManager(final TodoFinder todoFinder,
                       final TodoUpdater todoUpdater,
                       final TodoRemover todoRemover,
                       final JoinedMoimFinder joinedMoimFinder) {
        this.todoFinder = todoFinder;
        this.todoUpdater = todoUpdater;
        this.todoRemover = todoRemover;
        this.joinedMoimFinder = joinedMoimFinder;
    }

    @Transactional(readOnly = true)
    public List<TodoResponse> findMemberTodosInMoim(final long memberId, final Long moimId,
                                                    final YearMonth fromDate, final int months) {
        return todoFinder.findMemberTodosInMoim(memberId, moimId, fromDate, months);
    }

    @Transactional(readOnly = true)
    public List<MemberTodoResponse> findAllMembersTodosInMoim(final long moimId,
                                                              final YearMonth fromDate,
                                                              final int months) {
        LocalDate startDate = fromDate.atDay(MONTH_START_POINT.time());
        LocalDate endDate = fromDate.plusMonths(months).atEndOfMonth();

        List<Long> moimMemberIds = joinedMoimFinder.findAllJoinedMemberId(moimId);

        return moimMemberIds.stream().map(memberId -> {
            List<TodoResponse> todoResponses = todoFinder.findAllByDateRange(memberId, moimId, startDate, endDate);
            return MemberTodoResponse.of(memberId, todoResponses);
        }).toList();
    }

    @Transactional(readOnly = true)
    public TodoJpaEntity getById(final long todoId) {
        return todoFinder.getById(todoId);
    }

    @Transactional
    public void deleteTodo(final long memberId, final TodoRemoveRequest todoRemoveRequest) {
        long deleteTodoId = todoRemoveRequest.todoId();
        TodoJpaEntity originalTodo = todoFinder.getById(deleteTodoId);
        validateTodoOwner(memberId, originalTodo);
        todoRemover.deleteById(deleteTodoId);
    }

    @Transactional
    public TodoUpdateResponse updateTodoProgress(final long memberId, final long todoId, final TodoProgress todoProgress) {
        TodoJpaEntity findTodo = todoFinder.getById(todoId);
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
