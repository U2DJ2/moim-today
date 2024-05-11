package moim_today.implement.todo;

import moim_today.dto.todo.MemberTodoResponse;
import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;
import moim_today.persistence.entity.todo.TodoJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import static moim_today.util.TestConstant.MEMBER_ID;
import static moim_today.util.TestConstant.MOIM_ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TodoManagerTest extends ImplementTest {

    @Autowired
    private TodoManager todoManager;

    @DisplayName("모임의 모든 멤버의 시작, 끝 기간에 맞는 Todo를 모두 가져온다")
    @Test
    void findAllMembersTodosInMoim() {

        // given1
        final long MEMBER_SIZE = 4;
        final long EACH_MEMBER_TODO_SIZE_IN_MOIM = 2;

        for (long i = 0; i < MEMBER_SIZE; i++) {
            JoinedMoimJpaEntity joinedMoimJpaEntity = JoinedMoimJpaEntity.builder()
                    .memberId(MEMBER_ID.longValue() + i)
                    .moimId(MOIM_ID.longValue())
                    .build();

            joinedMoimRepository.save(joinedMoimJpaEntity);
        }

        for (long i = 0; i < MEMBER_SIZE; i++) {
            TodoJpaEntity todoJpaEntity1 = TodoJpaEntity.builder()
                    .memberId(MEMBER_ID.longValue() + i)
                    .moimId((MOIM_ID.longValue()))
                    .startDateTime(LocalDateTime.of(2024, 5, 11, 0, 0, 0))
                    .endDateTime(LocalDateTime.of(2024, 5, 11, 6, 0, 0))
                    .build();

            TodoJpaEntity todoJpaEntity2 = TodoJpaEntity.builder()
                    .memberId(MEMBER_ID.longValue() + i)
                    .moimId((MOIM_ID.longValue()))
                    .startDateTime(LocalDateTime.of(2024, 5, 11, 0, 0, 0))
                    .endDateTime(LocalDateTime.of(2024, 6, 1, 0, 0, 0))
                    .build();

            todoRepository.save(todoJpaEntity1);
            todoRepository.save(todoJpaEntity2);
        }

        // given2
        TodoJpaEntity outOfDateRangeTodo = TodoJpaEntity.builder()
                .memberId(MEMBER_ID.longValue())
                .moimId((MOIM_ID.longValue()))
                .startDateTime(LocalDateTime.of(2024, 5, 11, 0, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 7, 11, 0, 0, 0))
                .build();

        // given3
        TodoJpaEntity anotherMoimTodo = TodoJpaEntity.builder()
                .memberId(MEMBER_ID.longValue())
                .moimId((MOIM_ID.longValue())+1L)
                .startDateTime(LocalDateTime.of(2024, 5, 11, 0, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 6, 11, 0, 0, 0))
                .build();


        todoRepository.save(outOfDateRangeTodo);
        todoRepository.save(anotherMoimTodo);

        // when
        List<MemberTodoResponse> membersDataRangeTodosInMoim = todoManager.findAllMembersTodosInMoim(MOIM_ID.longValue(),
                YearMonth.of(2024, 5), 1);

        membersDataRangeTodosInMoim.forEach(System.out::println);

        // then
        assertThat(membersDataRangeTodosInMoim.size()).isEqualTo(MEMBER_SIZE);
        membersDataRangeTodosInMoim.forEach(m -> {
            assertThat(m.todoResponses().size()).isEqualTo(EACH_MEMBER_TODO_SIZE_IN_MOIM);
        });
    }
}