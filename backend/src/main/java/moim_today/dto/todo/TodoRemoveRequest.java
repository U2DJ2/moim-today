package moim_today.dto.todo;

import jakarta.validation.constraints.Min;

import static moim_today.global.constant.exception.ValidationExceptionConstant.TODO_ID_MIN_ERROR;

public record TodoRemoveRequest(
        @Min(value = 0, message = TODO_ID_MIN_ERROR) long todoId
) {

}
