package moim_today.dto.todo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import moim_today.domain.todo.enums.TodoProgress;
import moim_today.persistence.entity.todo.TodoJpaEntity;

import java.time.LocalDateTime;

@Builder
public record TodoUpdateResponse(
        String contents,
        TodoProgress todoProgress,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime startDateTime,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime endDateTime
) {
    public static TodoUpdateResponse from(final TodoJpaEntity originalTodo) {
        return TodoUpdateResponse.builder()
                .contents(originalTodo.getContents())
                .todoProgress(originalTodo.getTodoProgress())
                .startDateTime(originalTodo.getStartDateTime())
                .endDateTime(originalTodo.getEndDateTime())
                .build();
    }
}
