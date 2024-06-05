package moim_today.dto.todo;

import jakarta.validation.constraints.Min;

public record TodoRemoveRequest(
        @Min(value = 1, message = TODO_ID_MIN_ERROR) long todoId
) {
    private static final String TODO_ID_MIN_ERROR = "잘못된 할 일 ID 값이 입력 되었습니다.";
}
