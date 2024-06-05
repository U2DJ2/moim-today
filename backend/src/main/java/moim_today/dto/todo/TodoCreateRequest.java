package moim_today.dto.todo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import moim_today.domain.todo.enums.TodoProgress;
import moim_today.persistence.entity.todo.TodoJpaEntity;

import java.time.LocalDate;

@Builder
public record TodoCreateRequest(
        @Min(value = 1, message = MOIM_ID_MIN_ERROR) long moimId,
        @NotBlank(message = TODO_CONTENT_BLANK_ERROR) String contents,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        @NotNull(message = TODO_DATE_NULL_ERROR) LocalDate todoDate
) {
    private static final String MOIM_ID_MIN_ERROR = "잘못된 모임 ID 값이 들어왔습니다.";
    private static final String TODO_CONTENT_BLANK_ERROR = "할 일 내용은 공백일 수 없습니다.";
    private static final String TODO_DATE_NULL_ERROR = "할 일 마감 일자는 필수 입력 항목입니다.";

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
