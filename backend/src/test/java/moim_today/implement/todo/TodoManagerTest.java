package moim_today.implement.todo;

import moim_today.dto.todo.MemberTodoResponse;
import moim_today.dto.todo.TodoRemoveRequest;
import moim_today.dto.todo.TodoUpdateRequest;
import moim_today.global.error.ForbiddenException;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;
import moim_today.persistence.entity.todo.TodoJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static moim_today.domain.todo.enums.TodoProgress.COMPLETED;
import static moim_today.domain.todo.enums.TodoProgress.PENDING;
import static moim_today.global.constant.exception.TodoExceptionConstant.TODO_NOT_FOUND_ERROR;
import static moim_today.global.constant.exception.TodoExceptionConstant.TODO_NOT_OWNER_ERROR;
import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

class TodoManagerTest extends ImplementTest {

    @Autowired
    private TodoManager todoManager;

    private final LocalDate UPDATE_AFTER_TODO_DATE =
            LocalDate.of(2024, 5, 5);

    @DisplayName("모임의 모든 멤버의 요청이 들어온 기간 사이에 있는 Todo를 모두 가져온다")
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
                    .todoDate(LocalDate.of(2024, 5, 11))
                    .build();

            TodoJpaEntity todoJpaEntity2 = TodoJpaEntity.builder()
                    .memberId(MEMBER_ID.longValue() + i)
                    .moimId((MOIM_ID.longValue()))
                    .todoDate(LocalDate.of(2024, 5, 14))
                    .build();

            todoRepository.save(todoJpaEntity1);
            todoRepository.save(todoJpaEntity2);
        }

        // given2
        TodoJpaEntity outOfDateRangeTodo = TodoJpaEntity.builder()
                .memberId(MEMBER_ID.longValue())
                .moimId((MOIM_ID.longValue()))
                .todoDate(LocalDate.of(2024, 7, 11))
                .build();

        // given3
        TodoJpaEntity anotherMoimTodo = TodoJpaEntity.builder()
                .memberId(MEMBER_ID.longValue())
                .moimId((MOIM_ID.longValue())+1L)
                .todoDate(LocalDate.of(2024, 5, 12))
                .build();


        todoRepository.save(outOfDateRangeTodo);
        todoRepository.save(anotherMoimTodo);

        // when
        List<MemberTodoResponse> membersDataRangeTodosInMoim = todoManager.findAllMembersTodosInMoim(MOIM_ID.longValue(),
                YearMonth.of(2024, 5), 1);

        // then
        assertThat(membersDataRangeTodosInMoim.size()).isEqualTo(MEMBER_SIZE);
        membersDataRangeTodosInMoim.forEach(m -> {
            assertThat(m.todoResponses().size()).isEqualTo(EACH_MEMBER_TODO_SIZE_IN_MOIM);
        });
    }

    @DisplayName("Todo를 업데이트한다")
    @Test
    void updateTodoTest() {
        // given1
        TodoJpaEntity originalTodo = TodoJpaEntity.builder()
                .memberId(MEMBER_ID.longValue())
                .moimId(MOIM_ID.longValue())
                .contents(UPDATE_BEFORE_CONTENT.value())
                .todoProgress(PENDING)
                .todoDate(LocalDate.of(1000, 1, 1))
                .build();

        todoRepository.save(originalTodo);

        // given2
        TodoUpdateRequest todoUpdateRequest = new TodoUpdateRequest(originalTodo.getId(), MOIM_ID.longValue(),
                UPDATE_AFTER_CONTENT.value(), COMPLETED, UPDATE_AFTER_TODO_DATE);

        // expected
        assertThatCode(() -> todoManager.updateTodo(MEMBER_ID.longValue(), todoUpdateRequest))
                .doesNotThrowAnyException();
    }

    @DisplayName("Todo를 업데이트할 때, Todo가 존재하지 않으면 에러가 발생한다")
    @Test
    void updateTodoNotFoundErrorTest() {
        // given
        TodoUpdateRequest todoUpdateRequest = new TodoUpdateRequest(1, 1,
                UPDATE_AFTER_CONTENT.value(), PENDING, UPDATE_AFTER_TODO_DATE);

        // expected
        assertThatThrownBy(() -> todoManager.updateTodo(MEMBER_ID.longValue(), todoUpdateRequest))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(TODO_NOT_FOUND_ERROR.message());
    }

    @DisplayName("Todo를 업데이트할 때, Todo의 주인이 아니면 에러가 발생한다")
    @Test
    void updateTodoNotTodoOnwerErrorTest() {
        // given1
        TodoJpaEntity originalTodo = TodoJpaEntity.builder()
                .memberId(MEMBER_ID.longValue())
                .moimId(MOIM_ID.longValue())
                .contents(UPDATE_BEFORE_CONTENT.value())
                .todoProgress(PENDING)
                .todoDate(LocalDate.of(1000, 1, 1))
                .build();

        todoRepository.save(originalTodo);

        // given2
        TodoUpdateRequest todoUpdateRequest = new TodoUpdateRequest(originalTodo.getId(), MOIM_ID.longValue(),
                UPDATE_AFTER_CONTENT.value(), PENDING, UPDATE_AFTER_TODO_DATE);

        // expected
        assertThatThrownBy(() -> todoManager.updateTodo(MEMBER_ID.longValue()+1L, todoUpdateRequest))
                .isInstanceOf(ForbiddenException.class)
                .hasMessage(TODO_NOT_OWNER_ERROR.message());
    }

    @DisplayName("Todo를 삭제한다")
    @Test
    void deleteTodoTest() {
        // given1
        TodoJpaEntity originalTodo = TodoJpaEntity.builder()
                .memberId(MEMBER_ID.longValue())
                .moimId(MOIM_ID.longValue())
                .contents(UPDATE_BEFORE_CONTENT.value())
                .todoProgress(PENDING)
                .todoDate(LocalDate.of(1000, 1, 1))
                .build();

        todoRepository.save(originalTodo);

        // given2
        TodoRemoveRequest todoRemoveRequest = new TodoRemoveRequest(originalTodo.getId());

        // when
        todoManager.deleteTodo(MEMBER_ID.longValue(), todoRemoveRequest);

        // then
        assertThatThrownBy(() -> todoRepository.getById(originalTodo.getId()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(TODO_NOT_FOUND_ERROR.message());
    }

    @DisplayName("Todo를 삭제할 때, 존재하지 않는 Todo면 오류가 발생한다")
    @Test
    void deleteTodoNotFoundErrorTest() {
        // given1
        TodoJpaEntity originalTodo = TodoJpaEntity.builder()
                .memberId(MEMBER_ID.longValue())
                .moimId(MOIM_ID.longValue())
                .contents(UPDATE_BEFORE_CONTENT.value())
                .todoProgress(PENDING)
                .todoDate(LocalDate.of(1000, 1, 1))
                .build();

        // given2
        TodoRemoveRequest todoRemoveRequest = new TodoRemoveRequest(originalTodo.getId());

        // expected
        assertThatThrownBy(() -> todoManager.deleteTodo(MEMBER_ID.longValue(), todoRemoveRequest))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(TODO_NOT_FOUND_ERROR.message());
    }

    @DisplayName("Todo를 삭제할 때, 삭제 권한이 없으면 오류가 발생한다")
    @Test
    void deleteTodoNotOwnerErrorTest() {
        // given1
        TodoJpaEntity originalTodo = TodoJpaEntity.builder()
                .memberId(MEMBER_ID.longValue())
                .moimId(MOIM_ID.longValue())
                .contents(UPDATE_BEFORE_CONTENT.value())
                .todoProgress(PENDING)
                .todoDate(LocalDate.of(1000, 1, 1))
                .build();

        todoRepository.save(originalTodo);

        // given2
        TodoRemoveRequest todoRemoveRequest = new TodoRemoveRequest(originalTodo.getId());

        // expected
        assertThatThrownBy(() -> todoManager.deleteTodo(MEMBER_ID.longValue()+1L, todoRemoveRequest))
                .isInstanceOf(ForbiddenException.class)
                .hasMessage(TODO_NOT_OWNER_ERROR.message());
    }

    @DisplayName("투두 id로 투두를 조회한다")
    @Test
    void getById() {
        // given
        TodoJpaEntity originalTodo = TodoJpaEntity.builder()
                .memberId(MEMBER_ID.longValue())
                .moimId(MOIM_ID.longValue())
                .contents(UPDATE_BEFORE_CONTENT.value())
                .todoProgress(PENDING)
                .todoDate(LocalDate.of(1000, 1, 1))
                .build();

        todoRepository.save(originalTodo);

        // when
        TodoJpaEntity findTodo = todoManager.getById(originalTodo.getId());

        // then
        assertThat(findTodo.getMemberId()).isEqualTo(originalTodo.getMemberId());
        assertThat(findTodo.getContents()).isEqualTo(originalTodo.getContents());
    }

    @DisplayName("없는 투두면 에러를 발생시킨다")
    @Test
    void getByIdNoTodoError() {
        // expected
        assertThatThrownBy(() -> todoManager.getById(TODO_ID.longValue()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(TODO_NOT_FOUND_ERROR.message());
    }
}