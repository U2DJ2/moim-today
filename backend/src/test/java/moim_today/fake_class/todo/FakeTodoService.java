package moim_today.fake_class.todo;

import moim_today.application.todo.TodoService;
import moim_today.domain.todo.enums.TodoProgress;
import moim_today.dto.todo.*;
import moim_today.global.error.ForbiddenException;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.todo.TodoJpaEntity;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;

import static moim_today.domain.todo.enums.TodoProgress.COMPLETED;
import static moim_today.domain.todo.enums.TodoProgress.PENDING;
import static moim_today.global.constant.MemberConstant.UNKNOWN_MEMBER;
import static moim_today.global.constant.exception.JoinedMoimExceptionConstant.JOINED_MOIM_MEMBER_NOT_FOUND;
import static moim_today.global.constant.exception.TodoExceptionConstant.TODO_NOT_FOUND_ERROR;
import static moim_today.global.constant.exception.TodoExceptionConstant.TODO_NOT_OWNER_ERROR;
import static moim_today.util.TestConstant.*;

public class FakeTodoService implements TodoService {

    private final LocalDate TODO_DATE =
            LocalDate.of(2024, 5, 5);

    @Override
    public TodoCreateResponse createTodo(final long memberId, final TodoCreateRequest todoCreateRequest) {
        long moimId = todoCreateRequest.moimId();

        if (moimId == MOIM_ID.longValue() + 1L) {
            throw new NotFoundException(JOINED_MOIM_MEMBER_NOT_FOUND.message());
        }

        return TodoCreateResponse.from(TODO_ID.longValue());
    }

    @Override
    public List<MemberTodoResponse> findMembersTodosInMoim(final long requestMemberId, final long memberId, final long moimId,
                                                           final YearMonth requestDate, final int months) {
        if (moimId == MOIM_ID.longValue() + 1L) {
            throw new NotFoundException(JOINED_MOIM_MEMBER_NOT_FOUND.message());
        }

        TodoResponse todoResponse1 = TodoResponse.builder()
                .todoId(1L)
                .contents("첫 번째 할 일")
                .todoProgress(PENDING)
                .todoDate(LocalDate.of(2024, 5, 10))
                .build();

        TodoResponse todoResponse2 = TodoResponse.builder()
                .todoId(2L)
                .contents("두 번째 할 일")
                .todoProgress(TodoProgress.COMPLETED)
                .todoDate(LocalDate.of(2024, 5, 11))
                .build();

        MemberTodoResponse memberTodoResponse1 = MemberTodoResponse.of(
                100L,
                Arrays.asList(todoResponse1, todoResponse2));

        TodoResponse todoResponse3 = TodoResponse.builder()
                .todoId(3L)
                .contents("세 번째 할 일")
                .todoProgress(PENDING)
                .todoDate(LocalDate.of(2024, 5, 11))
                .build();

        TodoResponse todoResponse4 = TodoResponse.builder()
                .todoId(4L)
                .contents("네 번째 할 일")
                .todoProgress(TodoProgress.COMPLETED)
                .todoDate(LocalDate.of(2024, 5, 13))
                .build();

        MemberTodoResponse memberTodoResponse2 = MemberTodoResponse.of(
                101L,
                Arrays.asList(todoResponse3, todoResponse4));

        if (memberId == UNKNOWN_MEMBER.longValue()){
            return List.of(memberTodoResponse1, memberTodoResponse2);
        }
        return List.of(memberTodoResponse1);
    }

    @Override
    public TodoUpdateResponse updateTodo(final long memberId, final TodoUpdateRequest todoUpdateRequest) {
        long moimId = todoUpdateRequest.moimId();

        if (moimId == MOIM_ID.longValue() + 1L) {
            throw new NotFoundException(JOINED_MOIM_MEMBER_NOT_FOUND.message());
        } else if (moimId == MOIM_ID.longValue() + 3L) {
            throw new ForbiddenException(TODO_NOT_OWNER_ERROR.message());
        }

        return new TodoUpdateResponse(UPDATE_AFTER_CONTENT.value(),
                TodoProgress.COMPLETED, TODO_DATE
        );
    }

    @Override
    public void deleteTodo(final long memberId, final TodoRemoveRequest todoRemoveRequest) {
        long todoId = todoRemoveRequest.todoId();

        if (todoId == TODO_ID.longValue() + 1L) {
            throw new NotFoundException(TODO_NOT_FOUND_ERROR.message());
        } else if (todoId == MOIM_ID.longValue() + 2L) {
            throw new ForbiddenException(TODO_NOT_OWNER_ERROR.message());
        }
    }

    @Override
    public TodoResponse getById(final long todoId) {
        TodoJpaEntity originalTodo = TodoJpaEntity.builder()
                .memberId(MEMBER_ID.longValue())
                .moimId(MOIM_ID.longValue())
                .contents(UPDATE_BEFORE_CONTENT.value())
                .todoProgress(PENDING)
                .todoDate(LocalDate.of(2024, 1, 1))
                .build();

        return TodoResponse.from(originalTodo);
    }

    @Override
    public List<MemberMoimTodoResponse> findAllMemberTodos(final long memberId, final YearMonth requestDate, final int months) {
        TodoResponse todo1 = TodoResponse.builder()
                .todoId(TODO_ID.longValue())
                .todoProgress(COMPLETED)
                .contents("투두 완성하기")
                .todoDate(LocalDate.of(2024, 5, 15))
                .build();
        TodoResponse todo2 = TodoResponse.builder()
                .todoId(TODO_ID.longValue() + 1L)
                .todoProgress(PENDING)
                .contents("프로젝트 완성하기")
                .todoDate(LocalDate.of(2024, 6, 4))
                .build();
        TodoResponse todo3 = TodoResponse.builder()
                .todoId(TODO_ID.longValue() + 5L)
                .todoProgress(COMPLETED)
                .contents("운동하기")
                .todoDate(LocalDate.of(2024, 5, 15))
                .build();
        TodoResponse todo4 = TodoResponse.builder()
                .todoId(TODO_ID.longValue() + 3L)
                .todoProgress(PENDING)
                .contents("술 마시기")
                .todoDate(LocalDate.of(2024, 5, 20))
                .build();

        MemberMoimTodoResponse m1 = MemberMoimTodoResponse.of(MOIM_ID.longValue(), "U2DJ2 캡스톤 디자인", List.of(todo1, todo2));
        MemberMoimTodoResponse m2 = MemberMoimTodoResponse.of(MOIM_ID.longValue() + 2L, "오늘의 운동 완료 모임", List.of(todo3));
        MemberMoimTodoResponse m3 = MemberMoimTodoResponse.of(MOIM_ID.longValue() + 5L, "술이 문제야", List.of(todo4));

        return List.of(m1, m2, m3);
    }

    @Override
    public MemberMoimTodoResponse findMemberMoimTodosInMoim(final long memberId, final long moimId, final YearMonth requestDate, final int months) {
        return null;
    }

    @Override
    public TodoUpdateResponse updateTodoProgress(final long memberId, final TodoProgressUpdateRequest todoProgressUpdateRequest) {
        return TodoUpdateResponse.from(TodoJpaEntity.builder()
                .todoProgress(COMPLETED)
                .contents("투두 완성하기")
                .todoDate(LocalDate.of(2024, 5, 15))
                .build());
    }
}
