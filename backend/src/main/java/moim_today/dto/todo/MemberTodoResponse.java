package moim_today.dto.todo;

import lombok.Builder;

import java.util.List;

@Builder
public record MemberTodoResponse(
        long memberId,
        List<TodoResponse> todoResponses
) {

    public static MemberTodoResponse of(final long memberId, final List<TodoResponse> todoResponses){
        return MemberTodoResponse.builder()
                .memberId(memberId)
                .todoResponses(todoResponses)
                .build();
    }
}
