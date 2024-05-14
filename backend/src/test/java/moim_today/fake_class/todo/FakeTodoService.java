package moim_today.fake_class.todo;

import moim_today.application.todo.TodoService;
import moim_today.domain.todo.enums.TodoProgress;
import moim_today.dto.todo.*;
import moim_today.global.error.BadRequestException;
import moim_today.global.error.ForbiddenException;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.todo.TodoJpaEntity;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;

import static moim_today.global.constant.exception.JoinedMoimExceptionConstant.JOINED_MOIM_MEMBER_NOT_FOUND;
import static moim_today.global.constant.exception.TodoExceptionConstant.*;
import static moim_today.util.TestConstant.*;

public class FakeTodoService implements TodoService {

    private final LocalDateTime START_TIME =
            LocalDateTime.of(1, 1, 1, 1, 11, 1);
    private final LocalDateTime WRONG_UPDATE_AFTER_START_TIME =
            LocalDateTime.of(1200, 12, 12, 12, 8, 8, 8);
    private final LocalDateTime END_TIME =
            LocalDateTime.of(1200, 12, 12, 12, 8, 8, 8);
    private final LocalDateTime WRONG_UPDATE_AFTER_END_TIME =
            LocalDateTime.of(1, 1, 1, 1, 11, 1);

    @Override
    public TodoCreateResponse createTodo(final long memberId, final TodoCreateRequest todoCreateRequest) {
        long moimId = todoCreateRequest.moimId();

        if(moimId == MOIM_ID.longValue()+1L){
            throw new NotFoundException(JOINED_MOIM_MEMBER_NOT_FOUND.message());
        }else if(moimId == MOIM_ID.longValue()+2L){
            throw new BadRequestException(TODO_START_TIME_AFTER_END_TIME_ERROR.message());
        }

        return TodoCreateResponse.from(TODO_ID.longValue());
    }

    @Override
    public List<MemberTodoResponse> findAllMembersTodosInMoim(final long memberId, final long moimId,
                                                              final YearMonth startDate, final int months) {
        if(moimId == MOIM_ID.longValue()+1L){
            throw new NotFoundException(JOINED_MOIM_MEMBER_NOT_FOUND.message());
        }
        TodoResponse todoResponse1 = TodoResponse.builder()
                .todoId(1L)
                .contents("첫 번째 할 일")
                .todoProgress(TodoProgress.ACTIVE)
                .startDateTime(LocalDateTime.of(2024, 5, 10, 9, 0))
                .endDateTime(LocalDateTime.of(2024, 5, 10, 17, 0))
                .build();

        TodoResponse todoResponse2 = TodoResponse.builder()
                .todoId(2L)
                .contents("두 번째 할 일")
                .todoProgress(TodoProgress.COMPLETED)
                .startDateTime(LocalDateTime.of(2024, 5, 11, 9, 0))
                .endDateTime(LocalDateTime.of(2024, 5, 11, 17, 0))
                .build();

        MemberTodoResponse memberTodoResponse1 = MemberTodoResponse.builder()
                .memberId(100L)
                .todoResponses(Arrays.asList(todoResponse1, todoResponse2))
                .build();

        TodoResponse todoResponse3 = TodoResponse.builder()
                .todoId(3L)
                .contents("세 번째 할 일")
                .todoProgress(TodoProgress.PENDING)
                .startDateTime(LocalDateTime.of(2024, 5, 12, 9, 0))
                .endDateTime(LocalDateTime.of(2024, 5, 12, 17, 0))
                .build();

        TodoResponse todoResponse4 = TodoResponse.builder()
                .todoId(4L)
                .contents("네 번째 할 일")
                .todoProgress(TodoProgress.COMPLETED)
                .startDateTime(LocalDateTime.of(2024, 5, 13, 9, 0))
                .endDateTime(LocalDateTime.of(2024, 5, 13, 17, 0))
                .build();

        MemberTodoResponse memberTodoResponse2 = MemberTodoResponse.builder()
                .memberId(101L)
                .todoResponses(Arrays.asList(todoResponse3, todoResponse4))
                .build();

        return List.of(memberTodoResponse1, memberTodoResponse2);
    }

    @Override
    public TodoUpdateResponse updateTodo(final long memberId, final TodoUpdateRequest todoUpdateRequest) {
        long moimId = todoUpdateRequest.moimId();

        if(moimId == MOIM_ID.longValue()+1L){
            throw new NotFoundException(JOINED_MOIM_MEMBER_NOT_FOUND.message());
        }else if(moimId == MOIM_ID.longValue()+2L){
            throw new BadRequestException(TODO_START_TIME_AFTER_END_TIME_ERROR.message());
        }else if(moimId == MOIM_ID.longValue()+3L){
            throw new ForbiddenException(TODO_NOT_OWNER_ERROR.message());
        }

        TodoUpdateResponse todoUpdateResponse = new TodoUpdateResponse(UPDATE_AFTER_CONTENT.value(),
                TodoProgress.ACTIVE, START_TIME, END_TIME
        );

        return todoUpdateResponse;
    }

    @Override
    public void deleteTodo(final long memberId, final TodoRemoveRequest todoRemoveRequest) {
        long todoId = todoRemoveRequest.todoId();

        if(todoId == TODO_ID.longValue() + 1L){
            throw new NotFoundException(TODO_NOT_FOUND_ERROR.message());
        }else if(todoId == MOIM_ID.longValue() + 2L){
            throw new ForbiddenException(TODO_NOT_OWNER_ERROR.message());
        }
    }

    @Override
    public TodoDetailResponse getById(final long todoId) {
        TodoJpaEntity originalTodo = TodoJpaEntity.builder()
                .memberId(MEMBER_ID.longValue())
                .moimId(MOIM_ID.longValue())
                .contents(UPDATE_BEFORE_CONTENT.value())
                .todoProgress(TodoProgress.PENDING)
                .startDateTime(LocalDateTime.of(2024, 1, 1, 1, 1, 1))
                .endDateTime(LocalDateTime.of(2024, 5, 5, 5, 5, 5))
                .build();

        return TodoDetailResponse.from(originalTodo);
    }
}
