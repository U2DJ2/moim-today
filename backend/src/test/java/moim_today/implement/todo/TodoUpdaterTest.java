package moim_today.implement.todo;

import moim_today.domain.todo.enums.TodoProgress;
import moim_today.dto.todo.TodoUpdateRequest;
import moim_today.dto.todo.TodoUpdateResponse;
import moim_today.persistence.entity.todo.TodoJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static moim_today.domain.todo.enums.TodoProgress.COMPLETED;
import static moim_today.util.TestConstant.UPDATE_AFTER_CONTENT;
import static moim_today.util.TestConstant.UPDATE_BEFORE_CONTENT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TodoUpdaterTest extends ImplementTest {

    @Autowired
    private TodoUpdater todoUpdater;

    private final LocalDate UPDATE_AFTER_TODO_DATE =
            LocalDate.of(1, 1, 1);

    @DisplayName("Todo의 Entity를 업데이트한다.")
    @Test
    void updateTodoTest() {
        // given
        TodoJpaEntity originalTodo = TodoJpaEntity.builder()
                .contents(UPDATE_BEFORE_CONTENT.value())
                .todoProgress(TodoProgress.PENDING)
                .todoDate(LocalDate.of(1000, 1, 1))
                .build();

        todoRepository.save(originalTodo);

        TodoUpdateRequest todoUpdateRequest = new TodoUpdateRequest(originalTodo.getId(), originalTodo.getMoimId(),
                UPDATE_AFTER_CONTENT.value(), COMPLETED, UPDATE_AFTER_TODO_DATE);

        // when
        TodoUpdateResponse todoUpdateResponse = todoUpdater.updateTodo(originalTodo, todoUpdateRequest);

        // then
        assertThat(todoUpdateResponse.contents()).isEqualTo(UPDATE_AFTER_CONTENT.value());
        assertThat(todoUpdateResponse.todoProgress()).isEqualTo(COMPLETED);
        assertThat(todoUpdateResponse.todoDate()).isEqualTo(UPDATE_AFTER_TODO_DATE);
    }
}