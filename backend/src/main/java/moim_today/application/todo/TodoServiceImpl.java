package moim_today.application.todo;

import moim_today.domain.todo.enums.TodoProgress;
import moim_today.dto.todo.*;
import moim_today.implement.moim.joined_moim.JoinedMoimComposition;
import moim_today.implement.moim.moim.MoimComposition;
import moim_today.implement.todo.TodoComposition;
import moim_today.persistence.entity.todo.TodoJpaEntity;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;

import static moim_today.global.constant.MemberConstant.UNKNOWN_MEMBER;

@Service
public class TodoServiceImpl implements TodoService {

    private final JoinedMoimComposition joinedMoimComposition;
    private final TodoComposition todoComposition;
    private final MoimComposition moimComposition;

    public TodoServiceImpl(final JoinedMoimComposition joinedMoimComposition,
                           final TodoComposition todoComposition,
                           final MoimComposition moimComposition) {
        this.joinedMoimComposition = joinedMoimComposition;
        this.todoComposition = todoComposition;
        this.moimComposition = moimComposition;
    }

    @Override
    public TodoCreateResponse createTodo(final long memberId, final TodoCreateRequest todoCreateRequest) {
        joinedMoimComposition.validateMemberInMoim(memberId, todoCreateRequest.moimId());
        TodoJpaEntity todo = todoComposition.createTodo(memberId, todoCreateRequest);
        return TodoCreateResponse.from(todo.getId());
    }

    @Override
    public List<MemberMoimTodoResponse> findAllMemberTodos(final long memberId, final YearMonth requestDate, final int months) {
        List<Long> moimIds = joinedMoimComposition.findMoimIdsByMemberId(memberId);
        return moimIds.stream()
                .map(moimId -> findMemberMoimTodosInMoim(memberId, moimId, requestDate, months))
                .filter(memberMoimTodoResponse -> !memberMoimTodoResponse.todoGroupByDates().isEmpty())
                .toList();
    }

    @Override
    public List<MemberTodoResponse> findMembersTodosInMoim(final long requestMemberId,
                                                           final long memberId,
                                                           final long moimId,
                                                           final YearMonth requestDate,
                                                           final int months) {
        joinedMoimComposition.validateMemberInMoim(requestMemberId, moimId);
        if(UNKNOWN_MEMBER.longValue() == memberId){
            return todoComposition.findAllMembersTodosInMoim(moimId, requestDate, months);
        }
        List<TodoResponse> memberTodosInMoim = todoComposition.findMemberTodosInMoim(memberId, moimId, requestDate, months);
        return List.of(MemberTodoResponse.of(memberId,memberTodosInMoim));
    }

    @Override
    public TodoUpdateResponse updateTodo(final long memberId, final TodoUpdateRequest todoUpdateRequest) {
        joinedMoimComposition.validateMemberInMoim(memberId, todoUpdateRequest.moimId());
        return todoComposition.updateTodo(memberId, todoUpdateRequest);
    }

    @Override
    public void deleteTodo(final long memberId, final TodoRemoveRequest todoRemoveRequest) {
        todoComposition.deleteTodo(memberId, todoRemoveRequest);
    }

    @Override
    public TodoResponse getById(final long todoId) {
        TodoJpaEntity todo = todoComposition.getById(todoId);
        return TodoResponse.from(todo);
    }

    @Override
    public MemberMoimTodoResponse findMemberMoimTodosInMoim(final long memberId, final long moimId,
                                                            final YearMonth requestDate, final int months) {
        List<TodoResponse> todoResponses = todoComposition.findMemberTodosInMoim(memberId, moimId, requestDate, months);
        String moimTitle = moimComposition.getTitleById(moimId);
        return MemberMoimTodoResponse.of(moimId, moimTitle, todoResponses);
    }

    @Override
    public TodoUpdateResponse updateTodoProgress(final long memberId, final TodoProgressUpdateRequest todoProgressUpdateRequest) {
        long todoId = todoProgressUpdateRequest.todoId();
        TodoProgress todoProgress = todoProgressUpdateRequest.todoProgress();
        return todoComposition.updateTodoProgress(memberId, todoId, todoProgress);
    }
}
