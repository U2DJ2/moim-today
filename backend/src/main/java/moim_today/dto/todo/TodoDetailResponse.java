package moim_today.dto.todo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import moim_today.domain.todo.enums.TodoProgress;
import moim_today.persistence.entity.todo.TodoJpaEntity;

import java.time.LocalDate;

@Builder
public record TodoDetailResponse(
        long moimId,
        String contents,
        TodoProgress todoProgress,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate todoDate
) {

    public static TodoDetailResponse from(final TodoJpaEntity todoJpaEntity){
        return TodoDetailResponse.builder()
                .moimId(todoJpaEntity.getMoimId())
                .contents(todoJpaEntity.getContents())
                .todoProgress(todoJpaEntity.getTodoProgress())
                .todoDate(todoJpaEntity.getTodoDate())
                .build();
    }
}
