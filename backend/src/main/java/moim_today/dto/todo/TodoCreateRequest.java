package moim_today.dto.todo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import moim_today.domain.todo.enums.TodoProgress;
import moim_today.persistence.entity.todo.TodoJpaEntity;

import java.time.LocalDateTime;

@Builder
public record TodoCreateRequest(
        long moimId,
        String contents,

        @NotNull(message = "startDateTime 누락")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime startDateTime,

        @NotNull(message = "endDateTime 누락")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime endDateTime
) {

    public TodoJpaEntity toEntity(final long memberId){
        return TodoJpaEntity.builder()
                .moimId(moimId)
                .memberId(memberId)
                .contents(contents)
                .todoProgress(TodoProgress.PENDING)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .build();
    }

    public boolean checkStartBeforeOrEqualEnd(){
        return !startDateTime.isAfter(endDateTime);
    }
}
