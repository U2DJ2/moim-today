package moim_today.dto.todo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import moim_today.domain.todo.enums.TodoProgress;
import moim_today.persistence.entity.todo.TodoJpaEntity;

import java.time.LocalDate;

public record TodoUpdateRequest(
        long todoId,
        long moimId,
        String contents,
        TodoProgress todoProgress,

        @NotNull(message="todoDate 누락")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate todoDate
) {
    public TodoJpaEntity toEntity(final long memberId){
        return TodoJpaEntity.builder()
                .moimId(moimId)
                .memberId(memberId)
                .contents(contents)
                .todoProgress(todoProgress)
                .todoDate(todoDate)
                .build();
    }
}
