package moim_today.implement.todo;

import moim_today.persistence.entity.todo.TodoJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static moim_today.util.TestConstant.MOIM_ID;
import static org.assertj.core.api.Assertions.assertThat;

class TodoRemoverTest extends ImplementTest {

    @Autowired
    private TodoRemover todoRemover;

    @DisplayName("모임id에 해당하는 투두를 모두 삭제한다.")
    @Test
    void deleteTodosByMoimIdTest(){
        //given
        long moimId1 = Long.parseLong(MOIM_ID.value());
        long moimId2 = Long.parseLong(MOIM_ID.value()) + 1L;

        TodoJpaEntity todoJpaEntity1 = TodoJpaEntity.builder()
                .moimId(moimId1)
                .build();

        TodoJpaEntity todoJpaEntity2 = TodoJpaEntity.builder()
                .moimId(moimId1)
                .build();

        TodoJpaEntity todoJpaEntity3 = TodoJpaEntity.builder()
                .moimId(moimId2)
                .build();

        todoRepository.save(todoJpaEntity1);
        todoRepository.save(todoJpaEntity2);
        todoRepository.save(todoJpaEntity3);

        //when
        todoRemover.deleteAllByMoimId(moimId1);

        //then
        assertThat(todoRepository.count()).isEqualTo(1L);
    }
}