package moim_today.implement.todo;

import moim_today.dto.todo.TodoCreateRequest;
import moim_today.global.error.BadRequestException;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static moim_today.global.constant.exception.TodoExceptionConstant.TODO_START_TIME_AFTER_END_TIME_ERROR;
import static moim_today.util.TestConstant.MEMBER_ID;
import static moim_today.util.TestConstant.MOIM_ID;
import static org.assertj.core.api.Assertions.*;

class TodoAppenderTest extends ImplementTest {

    @Autowired
    private TodoAppender todoAppender;

    @DisplayName("모임에서 멤버가 투두를 추가한다")
    @Test
    void createTodo() {
        // given
        TodoCreateRequest todoCreateRequest = TodoCreateRequest.builder()
                .moimId(MOIM_ID.longValue())
                .startDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
                .build();

        // when
        todoAppender.createTodo(MEMBER_ID.longValue(), todoCreateRequest);


        // then
        int todoSize = todoRepository.getAllByMemberId(MEMBER_ID.longValue()).size();
        assertThat(todoSize).isEqualTo(1);
    }

    @DisplayName("투두를 추가할 때 시작 시간이 끝나는 시간보다 앞일 경우만 에러가 발생한다")
    @Test
    void createTodoStartTimeError() {
        // given
        TodoCreateRequest todoCreateRequest1 = TodoCreateRequest.builder()
                .moimId(MOIM_ID.longValue())
                .startDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 1, 0, 0))
                .build();

        TodoCreateRequest todoCreateRequest2 = TodoCreateRequest.builder()
                .moimId(MOIM_ID.longValue())
                .startDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 1, 1, 10, 0, 0))
                .build();

        // expected
        assertThatThrownBy(() -> todoAppender.createTodo(MEMBER_ID.longValue(), todoCreateRequest1))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(TODO_START_TIME_AFTER_END_TIME_ERROR.message());
        assertThatCode(() -> todoAppender.createTodo(MEMBER_ID.longValue(), todoCreateRequest2))
                .doesNotThrowAnyException();
    }
}