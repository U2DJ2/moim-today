package moim_today.implement.todo;

import moim_today.dto.todo.MemberTodoResponse;
import moim_today.dto.todo.TodoResponse;
import moim_today.global.annotation.Implement;
import moim_today.implement.moim.joined_moim.JoinedMoimFinder;
import moim_today.persistence.entity.todo.TodoJpaEntity;
import moim_today.persistence.repository.todo.TodoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static moim_today.global.constant.TimeConstant.MONTH_START_POINT;

@Implement
public class TodoFinder {

    private final TodoRepository todoRepository;
    private final JoinedMoimFinder joinedMoimFinder;

    public TodoFinder(final TodoRepository todoRepository, final JoinedMoimFinder joinedMoimFinder) {
        this.todoRepository = todoRepository;
        this.joinedMoimFinder = joinedMoimFinder;
    }

    @Transactional(readOnly = true)
    public List<TodoResponse> findMemberTodosInMoim(final long memberId, final Long moimId,
                                                    final YearMonth fromDate, final int months) {
        LocalDate startDate = fromDate.atDay(MONTH_START_POINT.time());
        LocalDate endDate = fromDate.plusMonths(months).atEndOfMonth();

        return todoRepository.findAllByDateRange(memberId, moimId, startDate, endDate);
    }

    @Transactional(readOnly = true)
    public List<TodoResponse> findAllByDateRange(final long memberId,
                                                 final long moimId,
                                                 final LocalDate startDate,
                                                 final LocalDate endDate) {
        return todoRepository.findAllByDateRange(memberId, moimId, startDate, endDate);
    }

    @Transactional(readOnly = true)
    public List<MemberTodoResponse> findAllMembersTodosInMoim(final long moimId,
                                                              final YearMonth fromDate,
                                                              final int months) {
        LocalDate startDate = fromDate.atDay(MONTH_START_POINT.time());
        LocalDate endDate = fromDate.plusMonths(months).atEndOfMonth();

        List<Long> moimMemberIds = joinedMoimFinder.findAllJoinedMemberId(moimId);

        return moimMemberIds.stream().map(memberId -> {
            List<TodoResponse> todoResponses = todoRepository.findAllByDateRange(memberId, moimId, startDate, endDate);
            return MemberTodoResponse.of(memberId, todoResponses);
        }).toList();
    }

    @Transactional(readOnly = true)
    public TodoJpaEntity getById(final long todoId){
        return todoRepository.getById(todoId);
    }
}
