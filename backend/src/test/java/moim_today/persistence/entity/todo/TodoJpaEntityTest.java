package moim_today.persistence.entity.todo;

import moim_today.domain.todo.enums.TodoProgress;
import moim_today.dto.todo.TodoUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static moim_today.util.TestConstant.UPDATE_AFTER_CONTENT;
import static moim_today.util.TestConstant.UPDATE_BEFORE_CONTENT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TodoJpaEntityTest {

    private final LocalDate UPDATE_AFTER_START_TIME
            = LocalDate.of(1, 1, 1);

    @DisplayName("Todo의 Entity를 업데이트한다.")
    @Test
    void updateTodo() {

        // given
        TodoJpaEntity todoJpaEntity = TodoJpaEntity.builder()
                .contents(UPDATE_BEFORE_CONTENT.value())
                .todoProgress(TodoProgress.PENDING)
                .todoDate(LocalDate.of(1000, 1, 1))
                .build();

        TodoUpdateRequest todoUpdateRequest = new TodoUpdateRequest(1, 1, UPDATE_AFTER_CONTENT.value(),
                TodoProgress.COMPLETED, UPDATE_AFTER_START_TIME);

        // when
        todoJpaEntity.updateTodo(todoUpdateRequest);

        // then
        assertThat(todoJpaEntity.getContents()).isEqualTo(UPDATE_AFTER_CONTENT.value());
        assertThat(todoJpaEntity.getTodoProgress()).isEqualTo(TodoProgress.COMPLETED);
        assertThat(todoJpaEntity.getTodoDate()).isEqualTo(UPDATE_AFTER_START_TIME);
    }
}