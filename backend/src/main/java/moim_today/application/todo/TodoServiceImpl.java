package moim_today.application.todo;

import moim_today.dto.todo.*;
import moim_today.implement.moim.joined_moim.JoinedMoimFinder;
import moim_today.implement.moim.joined_moim.JoinedMoimManager;
import moim_today.implement.todo.TodoAppender;
import moim_today.implement.todo.TodoManager;
import moim_today.persistence.entity.todo.TodoJpaEntity;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoAppender todoAppender;
    private final TodoManager todoManager;
    private final JoinedMoimManager joinedMoimManager;
    private final JoinedMoimFinder joinedMoimFinder;

    public TodoServiceImpl(final TodoAppender todoAppender,
                           final TodoManager todoManager,
                           final JoinedMoimManager joinedMoimManager,
                           final JoinedMoimFinder joinedMoimFinder) {
        this.todoAppender = todoAppender;
        this.todoManager = todoManager;
        this.joinedMoimManager = joinedMoimManager;
        this.joinedMoimFinder = joinedMoimFinder;
    }

    @Override
    public TodoCreateResponse createTodo(final long memberId, final TodoCreateRequest todoCreateRequest) {
        joinedMoimFinder.validateMemberInMoim(memberId, todoCreateRequest.moimId());
        TodoJpaEntity todo = todoAppender.createTodo(memberId, todoCreateRequest);
        return TodoCreateResponse.from(todo.getId());
    }

    @Override
    public List<MemberMoimTodoResponse> findAllMembersTodos(final long memberId, final YearMonth startDate, final int months) {
        List<Long> moimIds = joinedMoimManager.findMoimIdsByMemberId(memberId);
        List<MemberMoimTodoResponse> memberMoimTodoResponses = new ArrayList<>();
        return moimIds.stream()
                .map(moimId -> todoManager.findMemberTodosInMoim(memberId, moimId, startDate, months))
                .toList();
    }

    @Override
    public List<MemberTodoResponse> findAllMembersTodosInMoim(final long memberId,
                                                              final long moimId,
                                                              final YearMonth requestDate,
                                                              final int months) {
        joinedMoimFinder.validateMemberInMoim(memberId, moimId);
        return todoManager.findAllMembersTodosInMoim(moimId, requestDate, months);
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
    public TodoDetailResponse getById(final long todoId) {
        TodoJpaEntity todo = todoManager.getById(todoId);
        return TodoDetailResponse.from(todo);
    }
}
