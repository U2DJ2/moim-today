package moim_today.dto.todo;

import lombok.Builder;

import java.util.List;

@Builder
public record MemberMoimTodoResponse(
        long moimId,
        List<TodoResponse> todoResponses
) {
}
