package moim_today.application.todo;

import moim_today.dto.todo.MemberTodoResponse;
import moim_today.dto.todo.TodoCreateRequest;
import moim_today.dto.todo.TodoUpdateRequest;
import moim_today.implement.moim.joined_moim.JoinedMoimFinder;
import moim_today.implement.todo.TodoAppender;
import moim_today.implement.todo.TodoManager;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;

@Service
public class TodoServiceImpl implements TodoService{

    private final TodoAppender todoAppender;
    private final TodoManager todoManager;
    private final JoinedMoimFinder joinedMoimFinder;

    public TodoServiceImpl(final TodoAppender todoAppender,
                           final TodoManager todoManager,
                           final JoinedMoimFinder joinedMoimFinder) {
        this.todoAppender = todoAppender;
        this.todoManager = todoManager;
        this.joinedMoimFinder = joinedMoimFinder;
    }

    @Override
    public void createTodo(final long memberId, final TodoCreateRequest todoCreateRequest) {
        joinedMoimFinder.validateMemberInMoim(memberId, todoCreateRequest.moimId());
        todoAppender.createTodo(memberId, todoCreateRequest);
    }

    @Override
    public List<MemberTodoResponse> findAllMembersTodosInMoim(final long memberId,
                                                              final long moimId,
                                                              final YearMonth startDate,
                                                              final int months) {
        joinedMoimFinder.validateMemberInMoim(memberId, moimId);
        return todoManager.findAllMembersTodosInMoim(moimId, startDate, months);
    }

    @Override
    public void updateTodo(final long memberId, final TodoUpdateRequest todoUpdateRequest) {
        joinedMoimFinder.validateMemberInMoim(memberId, todoUpdateRequest.moimId());
        // Todo: 저장된 Todo가 있는지 검증하는 로직 추가
        // Todo: 투두 업데이트 권한이 있는 사용자인지 검사
        // Todo: 투두 업데이트 기능 Implement에 추가
    }
}
