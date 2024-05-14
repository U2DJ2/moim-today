package moim_today.implement.todo;

import moim_today.domain.todo.enums.TodoProgress;
import moim_today.dto.todo.TodoUpdateRequest;
import moim_today.dto.todo.TodoUpdateResponse;
import moim_today.persistence.entity.todo.TodoJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static moim_today.util.TestConstant.UPDATE_AFTER_CONTENT;
import static moim_today.util.TestConstant.UPDATE_BEFORE_CONTENT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TodoUpdaterTest extends ImplementTest {

    @Autowired
    private TodoUpdater todoUpdater;

    private final LocalDateTime UPDATE_AFTER_START_TIME =
            LocalDateTime.of(1, 1, 1, 1, 11, 1);
    private final LocalDateTime UPDATE_AFTER_END_TIME =
            LocalDateTime.of(1200, 12, 12, 12, 8, 8, 8);

    @DisplayName("Todo의 Entity를 업데이트한다.")
    @Test
    void updateTodoTest() {
        // given
        TodoJpaEntity originalTodo = TodoJpaEntity.builder()
                .contents(UPDATE_BEFORE_CONTENT.value())
                .todoProgress(TodoProgress.PENDING)
                .startDateTime(LocalDateTime.of(1000, 1, 1, 1, 1, 1))
                .endDateTime(LocalDateTime.of(1500, 5, 5, 5, 5, 5))
                .build();

        TodoUpdateRequest todoUpdateRequest = new TodoUpdateRequest(1, 1,
                UPDATE_AFTER_CONTENT.value(), TodoProgress.ACTIVE, UPDATE_AFTER_START_TIME, UPDATE_AFTER_END_TIME);

        // when
        TodoUpdateResponse todoUpdateResponse = todoUpdater.updateTodo(originalTodo, todoUpdateRequest);

        // then
        assertThat(todoUpdateResponse.contents()).isEqualTo(UPDATE_AFTER_CONTENT.value());
        assertThat(todoUpdateResponse.todoProgress()).isEqualTo(TodoProgress.ACTIVE);
        assertThat(todoUpdateResponse.startDateTime()).isEqualTo(UPDATE_AFTER_START_TIME);
        assertThat(todoUpdateResponse.endDateTime()).isEqualTo(UPDATE_AFTER_END_TIME);
    }
}