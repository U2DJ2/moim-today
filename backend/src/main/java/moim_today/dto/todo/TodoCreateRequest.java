package moim_today.dto.todo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import moim_today.domain.todo.enums.TodoProgress;
import moim_today.persistence.entity.todo.TodoJpaEntity;

import java.time.LocalDate;

import static moim_today.global.constant.exception.ValidationExceptionConstant.*;

@Builder
public record TodoCreateRequest(
        @Min(value = 0, message = MOIM_ID_MIN_ERROR) long moimId,
        @NotBlank(message = TODO_CONTENT_BLANK_ERROR) String contents,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        @NotNull(message = TODO_DATE_NULL_ERROR) LocalDate todoDate
) {

    public TodoJpaEntity toEntity(final long memberId){
        return TodoJpaEntity.builder()
                .moimId(moimId)
                .memberId(memberId)
                .contents(contents)
                .todoProgress(TodoProgress.PENDING)
                .todoDate(todoDate)
                .build();
    }
}
