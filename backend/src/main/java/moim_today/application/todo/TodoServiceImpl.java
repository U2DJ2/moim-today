package moim_today.application.todo;

import moim_today.domain.todo.enums.TodoProgress;
import moim_today.dto.todo.*;
import moim_today.implement.moim.joined_moim.JoinedMoimFinder;
import moim_today.implement.moim.joined_moim.JoinedMoimManager;
import moim_today.implement.moim.moim.MoimManager;
import moim_today.implement.todo.TodoAppender;
import moim_today.implement.todo.TodoManager;
import moim_today.persistence.entity.todo.TodoJpaEntity;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;

import static moim_today.global.constant.MemberConstant.UNKNOWN_MEMBER;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoAppender todoAppender;
    private final TodoManager todoManager;
    private final JoinedMoimManager joinedMoimManager;
    private final MoimManager moimManager;
    private final JoinedMoimFinder joinedMoimFinder;

    public TodoServiceImpl(final TodoAppender todoAppender,
                           final TodoManager todoManager,
                           final JoinedMoimManager joinedMoimManager,
                           final MoimManager moimManager,
                           final JoinedMoimFinder joinedMoimFinder) {
        this.todoAppender = todoAppender;
        this.todoManager = todoManager;
        this.joinedMoimManager = joinedMoimManager;
        this.moimManager = moimManager;
        this.joinedMoimFinder = joinedMoimFinder;
    }

    @Override
    public TodoCreateResponse createTodo(final long memberId, final TodoCreateRequest todoCreateRequest) {
        joinedMoimFinder.validateMemberInMoim(memberId, todoCreateRequest.moimId());
        TodoJpaEntity todo = todoAppender.createTodo(memberId, todoCreateRequest);
        return TodoCreateResponse.from(todo.getId());
    }

    @Override
    public List<MemberMoimTodoResponse> findAllMemberTodos(final long memberId, final YearMonth requestDate, final int months) {
        List<Long> moimIds = joinedMoimManager.findMoimIdsByMemberId(memberId);
        return moimIds.stream()
                .map(moimId -> findMemberMoimTodosInMoim(memberId, moimId, requestDate, months))
                .filter(memberMoimTodoResponse -> !memberMoimTodoResponse.todoResponses().isEmpty())
                .toList();
    }

    @Override
    public List<MemberTodoResponse> findMembersTodosInMoim(final long requestMemberId,
                                                           final long memberId,
                                                           final long moimId,
                                                           final YearMonth requestDate,
                                                           final int months) {
        joinedMoimFinder.validateMemberInMoim(requestMemberId, moimId);
        if(UNKNOWN_MEMBER.longValue() == memberId){
            return todoManager.findAllMembersTodosInMoim(moimId, requestDate, months);
        }
        List<TodoResponse> memberTodosInMoim = todoManager.findMemberTodosInMoim(memberId, moimId, requestDate, months);
        return List.of(MemberTodoResponse.of(memberId,memberTodosInMoim));
    }

    @Override
    public TodoUpdateResponse updateTodo(final long memberId, final TodoUpdateRequest todoUpdateRequest) {
        joinedMoimFinder.validateMemberInMoim(memberId, todoUpdateRequest.moimId());
        return todoManager.updateTodo(memberId, todoUpdateRequest);
    }

    @Override
    public void deleteTodo(final long memberId, final TodoRemoveRequest todoRemoveRequest) {
        todoManager.deleteTodo(memberId, todoRemoveRequest);
    }

    @Override
    public TodoResponse getById(final long todoId) {
        TodoJpaEntity todo = todoManager.getById(todoId);
        return TodoResponse.from(todo);
    }

    @Override
    public MemberMoimTodoResponse findMemberMoimTodosInMoim(final long memberId, final long moimId, final YearMonth requestDate,
                                                            final int months) {
        List<TodoResponse> todoResponses = todoManager.findMemberTodosInMoim(memberId, moimId, requestDate, months);
        String moimTitle = moimManager.getTitleById(moimId);
        return MemberMoimTodoResponse.builder()
                .moimId(moimId)
                .moimTitle(moimTitle)
                .todoResponses(todoResponses)
                .build();
    }

    @Override
    public TodoUpdateResponse updateTodoProgress(final long memberId, final TodoProgressUpdateRequest todoProgressUpdateRequest) {
        long todoId = todoProgressUpdateRequest.todoId();
        TodoProgress todoProgress = todoProgressUpdateRequest.todoProgress();
        return todoManager.updateTodoProgress(memberId, todoId, todoProgress);
    }
}
