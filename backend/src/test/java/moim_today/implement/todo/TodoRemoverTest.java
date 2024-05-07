package moim_today.implement.todo;

import moim_today.persistence.entity.todo.TodoJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static moim_today.util.TestConstant.MEMBER_ID;
import static moim_today.util.TestConstant.MOIM_ID;
import static org.assertj.core.api.Assertions.assertThat;

class TodoRemoverTest extends ImplementTest {

    @Autowired
    private TodoRemover todoRemover;

    @DisplayName("모임id에 해당하는 투두를 모두 삭제한다.")
    @Test
    void deleteTodosByMoimIdTest(){
        //given
        long moimId1 = MOIM_ID.longValue();
        long moimId2 = MOIM_ID.longValue() + 1L;

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

    @DisplayName("회원이 특정 모임에서 만들었던 투두를 모두 삭제한다")
    @Test
    void deleteAllTodosCreatedByMemberInMoim() {
        // given
        long moimId1 = MOIM_ID.longValue();
        long moimId2 = MOIM_ID.longValue() + 1L;
        long memberId = MEMBER_ID.longValue();

        TodoJpaEntity moim1Todo1 = TodoJpaEntity.builder()
                .moimId(moimId1)
                .memberId(memberId)
                .build();

        TodoJpaEntity moim1Todo2 = TodoJpaEntity.builder()
                .moimId(moimId1)
                .memberId(memberId)
                .build();

        TodoJpaEntity moim2Todo1 = TodoJpaEntity.builder()
                .moimId(moimId2)
                .memberId(memberId)
                .build();

        TodoJpaEntity moim2Todo2 = TodoJpaEntity.builder()
                .moimId(moimId2)
                .memberId(memberId)
                .build();

        todoRepository.save(moim1Todo1);
        todoRepository.save(moim1Todo2);
        todoRepository.save(moim2Todo1);
        todoRepository.save(moim2Todo2);

        // when
        todoRemover.deleteAllTodosCreatedByMemberInMoim(moimId1, memberId);

        // then
        assertThat(todoRepository.count())
                .isEqualTo(2L);
    }

    @DisplayName("회원이 특정 모임에서 만들었던 투두만 삭제한다")
    @Test
    void shouldOnlyDeleteAllTodosInSpecifiedMoim() {
        // given
        long moimId1 = MOIM_ID.longValue();
        long moimId2 = MOIM_ID.longValue() + 1L;
        long memberId = MEMBER_ID.longValue();

        TodoJpaEntity moim1Todo1 = TodoJpaEntity.builder()
                .moimId(moimId1)
                .memberId(memberId)
                .build();

        TodoJpaEntity moim1Todo2 = TodoJpaEntity.builder()
                .moimId(moimId1)
                .memberId(memberId)
                .build();

        TodoJpaEntity moim2Todo1 = TodoJpaEntity.builder()
                .moimId(moimId2)
                .memberId(memberId)
                .build();

        TodoJpaEntity moim2Todo2 = TodoJpaEntity.builder()
                .moimId(moimId2)
                .memberId(memberId)
                .build();

        todoRepository.save(moim1Todo1);
        todoRepository.save(moim1Todo2);
        todoRepository.save(moim2Todo1);
        todoRepository.save(moim2Todo2);

        // when
        todoRemover.deleteAllTodosCreatedByMemberInMoim(moimId1, memberId);

        // then
        assertThat(todoRepository.getAllTodosByMemberId(memberId).size())
                .isEqualTo(2);
    }
}
