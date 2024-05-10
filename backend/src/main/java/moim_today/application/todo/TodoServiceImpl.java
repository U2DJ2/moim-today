package moim_today.application.todo;

import moim_today.dto.todo.TodoCreateRequest;
import moim_today.implement.moim.joined_moim.JoinedMoimFinder;
import moim_today.implement.todo.TodoAppender;
import org.springframework.stereotype.Service;

@Service
public class TodoServiceImpl implements TodoService{

    private final TodoAppender todoAppender;
    private final JoinedMoimFinder joinedMoimFinder;

    public TodoServiceImpl(final TodoAppender todoAppender,
                           final JoinedMoimFinder joinedMoimFinder) {
        this.todoAppender = todoAppender;
        this.joinedMoimFinder = joinedMoimFinder;
    }

    @Override
    public void createTodo(final long memberId, final TodoCreateRequest todoCreateRequest) {
        joinedMoimFinder.validateMemberInMoim(todoCreateRequest.moimId(), memberId);

        todoAppender.createTodo(memberId, todoCreateRequest);
    }
}
