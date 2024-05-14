package moim_today.dto.todo;

public record TodoCreateResponse(
        long todoId
) {
    public static TodoCreateResponse from(final long todoId){
        return new TodoCreateResponse(todoId);
    }
}
