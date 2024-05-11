package moim_today.implement.todo;

import moim_today.dto.todo.TodoResponse;
import moim_today.persistence.entity.todo.TodoJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
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
        TodoJpaEntity todoJpa1 = TodoJpaEntity.builder()
                .memberId(MEMBER_ID.longValue())
                .moimId((MOIM_ID.longValue()))
                .startDateTime(LocalDateTime.of(2024, 5, 11, 0, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 5, 11, 0, 0, 0))
                .build();

        TodoJpaEntity todoJpa2 = TodoJpaEntity.builder()
                .memberId(MEMBER_ID.longValue())
                .moimId((MOIM_ID.longValue()))
                .startDateTime(LocalDateTime.of(2024, 5, 10, 0, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 5, 12, 0, 0, 0))
                .build();

        TodoJpaEntity todoJpa3 = TodoJpaEntity.builder()
                .memberId(MEMBER_ID.longValue())
                .moimId((MOIM_ID.longValue()))
                .startDateTime(LocalDateTime.of(2024, 5, 10, 0, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 5, 13, 0, 0, 0))
                .build();

        todoRepository.save(todoJpa1);
        todoRepository.save(todoJpa2);
        todoRepository.save(todoJpa3);

        LocalDateTime testStartTime = LocalDateTime.of(2024, 5, 10, 0, 0, 0);
        LocalDateTime testEndTime = LocalDateTime.of(2024, 5, 12, 0, 0, 0);
        List<TodoResponse> todoResponses = todoFinder.findAllByDateRange(MEMBER_ID.longValue(), MOIM_ID.longValue(),
                testStartTime, testEndTime);

        assertThat(todoResponses.size()).isEqualTo(2);
    }

    @DisplayName("다른 모임이나 다른 멤버의 Todo는 가져오지 않는다")
    @Test
    void findAllByDateRangeExceptAnother() {
        TodoJpaEntity todoJpa1 = TodoJpaEntity.builder()
                .memberId(MEMBER_ID.longValue())
                .moimId((MOIM_ID.longValue()))
                .startDateTime(LocalDateTime.of(2024, 5, 11, 0, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 5, 11, 0, 0, 0))
                .build();

        TodoJpaEntity todoJpa2 = TodoJpaEntity.builder()
                .memberId(MEMBER_ID.longValue())
                .moimId((MOIM_ID.longValue()+1L))
                .startDateTime(LocalDateTime.of(2024, 5, 10, 0, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 5, 12, 0, 0, 0))
                .build();

        TodoJpaEntity todoJpa3 = TodoJpaEntity.builder()
                .memberId(MEMBER_ID.longValue()+1L)
                .moimId((MOIM_ID.longValue()))
                .startDateTime(LocalDateTime.of(2024, 5, 10, 0, 0, 0))
                .endDateTime(LocalDateTime.of(2024, 5, 12, 0, 0, 0))
                .build();

        todoRepository.save(todoJpa1);
        todoRepository.save(todoJpa2);
        todoRepository.save(todoJpa3);

        LocalDateTime testStartTime = LocalDateTime.of(2024, 5, 10, 0, 0, 0);
        LocalDateTime testEndTime = LocalDateTime.of(2024, 5, 12, 0, 0, 0);
        List<TodoResponse> todoResponses = todoFinder.findAllByDateRange(MEMBER_ID.longValue(), MOIM_ID.longValue(),
                testStartTime, testEndTime);

        assertThat(todoResponses.size()).isEqualTo(1);
    }
}