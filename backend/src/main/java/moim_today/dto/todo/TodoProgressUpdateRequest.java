package moim_today.dto.todo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import moim_today.domain.todo.enums.TodoProgress;

import static moim_today.global.constant.exception.ValidationExceptionConstant.TODO_ID_MIN_ERROR;
import static moim_today.global.constant.exception.ValidationExceptionConstant.TODO_PROGRESS_NULL_ERROR;

public record TodoProgressUpdateRequest(
        @Min(value = 0, message = TODO_ID_MIN_ERROR) long todoId,
        @NotNull(message = TODO_PROGRESS_NULL_ERROR) TodoProgress todoProgress
) {

}
