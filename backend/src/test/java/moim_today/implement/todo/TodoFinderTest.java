package moim_today.implement.todo;

import moim_today.dto.todo.TodoResponse;
import moim_today.persistence.entity.todo.TodoJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static moim_today.util.TestConstant.MEMBER_ID;
import static moim_today.util.TestConstant.MOIM_ID;
import static org.assertj.core.api.Assertions.assertThat;

class TodoFinderTest extends ImplementTest {

    @Autowired
    private TodoFinder todoFinder;

    @DisplayName("모임에 있는 멤버의 시작 시간 ~ 끝나는 시간 사이의 모든 Todo를 가져온다")
    @Test
    void findAllByDateRange() {

        //given1
        List<LocalDate> shouldInvolveTodoDate = List.of(
                LocalDate.of(2024, 5, 10),
                LocalDate.of(2024, 5, 11)
        );

        TodoJpaEntity todoJpa1 = TodoJpaEntity.builder()
                .memberId(MEMBER_ID.longValue())
                .moimId((MOIM_ID.longValue()))
                .todoDate(shouldInvolveTodoDate.get(0))
                .build();

        TodoJpaEntity todoJpa2 = TodoJpaEntity.builder()
                .memberId(MEMBER_ID.longValue())
                .moimId((MOIM_ID.longValue()))
                .todoDate(shouldInvolveTodoDate.get(1))
                .build();

        todoRepository.save(todoJpa1);
        todoRepository.save(todoJpa2);

        //given2
        List<LocalDate> shouldNotInvolveTodoDate = List.of(
                LocalDate.of(2024, 5, 9),
                LocalDate.of(2024, 5, 13)
        );

        TodoJpaEntity todoJpa3 = TodoJpaEntity.builder()
                .memberId(MEMBER_ID.longValue())
                .moimId((MOIM_ID.longValue()))
                .todoDate(shouldNotInvolveTodoDate.get(0))
                .build();

        TodoJpaEntity todoJpa4 = TodoJpaEntity.builder()
                .memberId(MEMBER_ID.longValue())
                .moimId((MOIM_ID.longValue()))
                .todoDate(shouldNotInvolveTodoDate.get(1))
                .build();

        todoRepository.save(todoJpa3);
        todoRepository.save(todoJpa4);

        // when
        LocalDate getTodoFrom = LocalDate.of(2024, 5, 10);
        LocalDate getTodoTo = LocalDate.of(2024, 5, 12);
        List<TodoResponse> todoResponses = todoFinder.findAllByDateRange(MEMBER_ID.longValue(), MOIM_ID.longValue(),
                getTodoFrom, getTodoTo);

        // then
        assertThat(todoResponses.size()).isEqualTo(2);
        todoResponses.forEach(todoResponse -> {
            assertThat(todoResponse.todoDate()).isIn(shouldInvolveTodoDate);
        });
    }

    @DisplayName("다른 모임이나 다른 멤버의 Todo는 가져오지 않는다")
    @Test
    void findAllByDateRangeExceptAnother() {

        // given1
        TodoJpaEntity todoJpa1 = TodoJpaEntity.builder()
                .memberId(MEMBER_ID.longValue())
                .moimId((MOIM_ID.longValue()))
                .todoDate(LocalDate.of(2024, 5, 10))
                .build();

        TodoJpaEntity todoJpa2 = TodoJpaEntity.builder()
                .memberId(MEMBER_ID.longValue())
                .moimId((MOIM_ID.longValue()))
                .todoDate(LocalDate.of(2024, 5, 11))
                .build();

        TodoJpaEntity todoJpa3 = TodoJpaEntity.builder()
                .memberId(MEMBER_ID.longValue())
                .moimId((MOIM_ID.longValue()))
                .todoDate(LocalDate.of(2024, 5, 12))
                .build();

        // given2
        TodoJpaEntity todoJpa4 = TodoJpaEntity.builder()
                .memberId(MEMBER_ID.longValue() + 1L)
                .moimId((MOIM_ID.longValue()))
                .todoDate(LocalDate.of(2024, 5, 10))
                .build();


        TodoJpaEntity todoJpa5 = TodoJpaEntity.builder()
                .memberId(MEMBER_ID.longValue())
                .moimId((MOIM_ID.longValue()))
                .todoDate(LocalDate.of(2024, 5, 13))
                .build();

        todoRepository.save(todoJpa1);
        todoRepository.save(todoJpa2);
        todoRepository.save(todoJpa3);
        todoRepository.save(todoJpa4);
        todoRepository.save(todoJpa5);

        LocalDate getTodoFrom = LocalDate.of(2024, 5, 10);
        LocalDate getTodoTo = LocalDate.of(2024, 5, 12);
        List<TodoResponse> todoResponses = todoFinder.findAllByDateRange(MEMBER_ID.longValue(), MOIM_ID.longValue(),
                getTodoFrom, getTodoTo);

        assertThat(todoResponses.size()).isEqualTo(3);
    }
}