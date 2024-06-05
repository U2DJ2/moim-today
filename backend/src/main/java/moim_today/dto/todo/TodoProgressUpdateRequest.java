package moim_today.dto.todo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import moim_today.domain.todo.enums.TodoProgress;

public record TodoProgressUpdateRequest(
        @Min(value = 1, message = TODO_ID_MIN_ERROR) long todoId,
        @NotNull(message = TODO_PROGRESS_NULL_ERROR) TodoProgress todoProgress
) {
    private static final String TODO_ID_MIN_ERROR = "잘못된 할 일 ID 값이 입력 되었습니다.";
    private static final String TODO_PROGRESS_NULL_ERROR = "할 일 진행도는 필수 입력 항목입니다.";
}
