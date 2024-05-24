package moim_today.domain.todo;

import moim_today.dto.todo.TodoResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static moim_today.domain.todo.enums.TodoProgress.PENDING;
import static moim_today.util.TestConstant.TODO_ID;
import static org.assertj.core.api.Assertions.assertThat;

class TodoGroupByDateTest {

    @DisplayName("TodoResponse를 TodoGroupByDate로 변환할 수 있다")
    @Test
    void todoGroupByDates() {
        final String contents = "투두 내용";
        final LocalDate localDate = LocalDate.of(2024,5,25);

        // given
        TodoResponse todoResponse1 = TodoResponse.builder()
                .todoId(TODO_ID.longValue())
                .contents(contents)
                .todoDate(localDate)
                .todoProgress(PENDING)
                .build();
        TodoResponse todoResponse2 = TodoResponse.builder()
                .todoId(TODO_ID.longValue() + 1L)
                .contents(contents)
                .todoDate(localDate)
                .todoProgress(PENDING)
                .build();
        TodoResponse todoResponse3 = TodoResponse.builder()
                .todoId(TODO_ID.longValue() + 2L)
                .contents(contents)
                .todoDate(localDate)
                .todoProgress(PENDING)
                .build();

        List<TodoResponse> todoResponses = List.of(todoResponse1, todoResponse2, todoResponse3);

        // when
        List<TodoGroupByDate> todoGroupByDates = TodoGroupByDate.todoGroupByDates(todoResponses);

        // then
        assertThat(todoGroupByDates).hasSize(1);
        assertThat(todoGroupByDates.get(0).todoDate()).isEqualTo(localDate);

        List<TodoContents> todoContents = todoGroupByDates.get(0).todoContents();
        assertThat(todoContents).hasSize(3);
        assertThat(todoContents).extracting("todoId")
                .containsExactlyInAnyOrder(todoResponse1.todoId(), todoResponse2.todoId(), todoResponse3.todoId());
        assertThat(todoContents).extracting("contents")
                .containsOnly(contents);
        assertThat(todoContents).extracting("todoProgress")
                .containsOnly(PENDING);
    }
}