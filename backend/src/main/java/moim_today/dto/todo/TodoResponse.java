package moim_today.dto.todo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import moim_today.domain.todo.enums.TodoProgress;
import moim_today.persistence.entity.todo.TodoJpaEntity;

import java.time.LocalDateTime;

@Builder
public record TodoResponse(
        long todoId,
        String contents,
        TodoProgress todoProgress,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime startDateTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime endDateTime
) {

    @QueryProjection
    public TodoResponse{
    }

    public static TodoResponse of(final TodoJpaEntity todo) {
        return TodoResponse.builder()
                .todoId(todo.getId())
                .contents(todo.getContents())
                .todoProgress(todo.getTodoProgress())
                .startDateTime(todo.getStartDateTime())
                .endDateTime(todo.getEndDateTime())
                .build();
    }
}