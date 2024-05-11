package moim_today.implement.todo;

import moim_today.dto.todo.MemberTodoResponse;
import moim_today.dto.todo.TodoResponse;
import moim_today.global.annotation.Implement;
import moim_today.implement.moim.joined_moim.JoinedMoimManager;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import static moim_today.global.constant.TimeConstant.MONTH_START_POINT;

@Implement
public class TodoManager {

    private final TodoFinder todoFinder;
    private final JoinedMoimManager joinedMoimManager;


    public TodoManager(final TodoFinder todoFinder,
                       final JoinedMoimManager joinedMoimManager) {
        this.todoFinder = todoFinder;
        this.joinedMoimManager = joinedMoimManager;
    }

    public List<MemberTodoResponse> findAllMembersTodosInMoim(final long memberId,
                                                              final long moimId,
                                                              final YearMonth startDate,
                                                              final int months) {
        LocalDateTime startDateTime = startDate.atDay(MONTH_START_POINT.time()).atStartOfDay();
        LocalDateTime endDateTime = startDateTime.plusMonths(months);

        List<Long> moimMemberIds = joinedMoimManager.findAllJoinedMemberId(moimId);

        return moimMemberIds.stream().map(m -> {
            List<TodoResponse> todoResponses = todoFinder.findAllByDateRange(memberId, moimId, startDateTime, endDateTime);
            return MemberTodoResponse.of(memberId, todoResponses);
        }).toList();
    }
}
