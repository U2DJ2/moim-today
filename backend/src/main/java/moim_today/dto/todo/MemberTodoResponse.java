package moim_today.dto.todo;

import lombok.Builder;
import moim_today.domain.todo.TodoGroupByDate;

import java.util.List;

@Builder
public record MemberTodoResponse(
        long memberId,
        List<TodoGroupByDate> todoGroupByDates
) {

    public static MemberTodoResponse of(final long memberId, final List<TodoResponse> todoResponses){
        List<TodoGroupByDate> todoGroupByDates = TodoGroupByDate.todoGroupByDates(todoResponses);
        return MemberTodoResponse.builder()
                .memberId(memberId)
                .todoGroupByDates(todoGroupByDates)
                .build();
    }
}
