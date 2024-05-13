package moim_today.dto.todo;

public record TodoCreateResponse(
        long todoId
) {
    public static TodoCreateResponse of(final long todoId){
        return new TodoCreateResponse(todoId);
    }
}
