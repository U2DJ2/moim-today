package moim_today.persistence.entity.todo;

import moim_today.domain.todo.enums.TodoProgress;
import moim_today.dto.todo.TodoUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TodoJpaEntityTest {

    private final String UPDATE_BEFORE_CONTENT = "업데이트하기 전 Todo Content";
    private final String UPDATE_AFTER_CONTENT = "업데이트한 이후 Todo Content";
    private final LocalDateTime UPDATE_AFTER_START_TIME
            = LocalDateTime.of(1, 1, 1, 1, 11, 1);
    private final LocalDateTime UPDATE_AFTER_END_TIME
            = LocalDateTime.of(1200, 12, 12, 12, 8, 8, 8);

    @DisplayName("Todo의 Entity를 업데이트한다.")
    @Test
    void updateTodo() {

        // given
        TodoJpaEntity todoJpaEntity = TodoJpaEntity.builder()
                .contents(UPDATE_BEFORE_CONTENT)
                .todoProgress(TodoProgress.PENDING)
                .startDateTime(LocalDateTime.of(1000, 1, 1, 1, 1, 1))
                .endDateTime(LocalDateTime.of(1500, 5, 5, 5, 5, 5, 5))
                .build();

        TodoUpdateRequest todoUpdateRequest = new TodoUpdateRequest(1, 1, UPDATE_AFTER_CONTENT,
                TodoProgress.ACTIVE, UPDATE_AFTER_START_TIME, UPDATE_AFTER_END_TIME);

        // when
        todoJpaEntity.updateTodo(todoUpdateRequest);


        // then
        assertThat(todoJpaEntity.getContents()).isEqualTo(UPDATE_AFTER_CONTENT);
        assertThat(todoJpaEntity.getTodoProgress()).isEqualTo(TodoProgress.ACTIVE);
        assertThat(todoJpaEntity.getStartDateTime()).isEqualTo(UPDATE_AFTER_START_TIME);
        assertThat(todoJpaEntity.getEndDateTime()).isEqualTo(UPDATE_AFTER_END_TIME);
    }
}