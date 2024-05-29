package moim_today.dto.todo;

import lombok.Builder;
import moim_today.domain.todo.TodoGroupByDate;

import java.util.List;

@Builder
public record MemberMoimTodoResponse(
        long moimId,
        String moimTitle,
        List<TodoGroupByDate> todoGroupByDates
) {

    public static MemberMoimTodoResponse of(final long moimId, final String moimTitle, final List<TodoResponse> todoResponses){
        List<TodoGroupByDate> todoGroupByDates = TodoGroupByDate.todoGroupByDates(todoResponses);
        return MemberMoimTodoResponse.builder()
                .moimId(moimId)
                .moimTitle(moimTitle)
                .todoGroupByDates(todoGroupByDates)
                .build();
    }
}
