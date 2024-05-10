package moim_today.implement.todo;

import moim_today.dto.todo.TodoCreateRequest;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static moim_today.util.TestConstant.MEMBER_ID;
import static moim_today.util.TestConstant.MOIM_ID;
import static org.assertj.core.api.Assertions.assertThat;

class TodoAppenderTest extends ImplementTest {

    @Autowired
    private TodoAppender todoAppender;

    @DisplayName("모임에서 멤버가 투두를 추가한다")
    @Test
    void createTodo() {
        // given
        TodoCreateRequest todoCreateRequest = TodoCreateRequest.builder()
                .moimId(MOIM_ID.longValue())
                .build();

        // when
        todoAppender.createTodo(MEMBER_ID.longValue(), todoCreateRequest);


        // then
        int todoSize = todoRepository.getAllTodosByMemberId(MEMBER_ID.longValue()).size();
        assertThat(todoSize).isEqualTo(1);
    }
}