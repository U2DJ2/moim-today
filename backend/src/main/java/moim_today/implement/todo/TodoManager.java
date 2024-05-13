package moim_today.implement.todo;

import moim_today.dto.todo.MemberTodoResponse;
import moim_today.dto.todo.TodoResponse;
import moim_today.dto.todo.TodoUpdateRequest;
import moim_today.dto.todo.TodoUpdateResponse;
import moim_today.global.annotation.Implement;
import moim_today.global.error.ForbiddenException;
import moim_today.implement.moim.joined_moim.JoinedMoimManager;
import moim_today.persistence.entity.todo.TodoJpaEntity;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import static moim_today.global.constant.TimeConstant.MONTH_START_POINT;
import static moim_today.global.constant.exception.TodoExceptionConstant.TODO_NOT_OWNER_ERROR;

@Implement
public class TodoManager {

    private final TodoFinder todoFinder;
    private final TodoUpdater todoUpdater;
    private final JoinedMoimManager joinedMoimManager;


    public TodoManager(final TodoFinder todoFinder,
                       final TodoUpdater todoUpdater,
                       final JoinedMoimManager joinedMoimManager) {
        this.todoFinder = todoFinder;
        this.todoUpdater = todoUpdater;
        this.joinedMoimManager = joinedMoimManager;
    }

    public List<MemberTodoResponse> findAllMembersTodosInMoim(final long moimId,
                                                              final YearMonth startDate,
                                                              final int months) {
        LocalDateTime startDateTime = startDate.atDay(MONTH_START_POINT.time()).atStartOfDay();
        LocalDateTime endDateTime = startDateTime.plusMonths(months)
                .with(TemporalAdjusters.lastDayOfMonth())
                .withHour(23)
                .withMinute(59)
                .withSecond(59);

        List<Long> moimMemberIds = joinedMoimManager.findAllJoinedMemberId(moimId);

        return moimMemberIds.stream().map(m -> {
            List<TodoResponse> todoResponses = todoFinder.findAllByDateRange(m, moimId, startDateTime, endDateTime);
            return MemberTodoResponse.of(m, todoResponses);
        }).toList();
    }

    public TodoUpdateResponse updateTodo(final long memberId, final TodoUpdateRequest todoUpdateRequest){
        TodoJpaEntity originalTodo = todoFinder.getById(todoUpdateRequest.todoId());
        if(isTodoOwner(memberId, originalTodo)){
            return todoUpdater.updateTodo(originalTodo, todoUpdateRequest);
        }else{
            throw new ForbiddenException(TODO_NOT_OWNER_ERROR.message());
        }
    }

    private boolean isTodoOwner(final long memberId, final TodoJpaEntity todoJpaEntity){
        return todoJpaEntity.getMemberId() == memberId;
    }
}
