package moim_today.application.todo;

import moim_today.dto.todo.TodoCreateRequest;
import moim_today.dto.todo.TodoFinder;
import moim_today.dto.todo.TodoResponse;
import moim_today.implement.moim.joined_moim.JoinedMoimFinder;
import moim_today.implement.todo.TodoAppender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService{

    private final TodoAppender todoAppender;
    private final TodoFinder todoFinder;
    private final JoinedMoimFinder joinedMoimFinder;

    public TodoServiceImpl(final TodoAppender todoAppender,
                           final TodoFinder todoFinder,
                           final JoinedMoimFinder joinedMoimFinder) {
        this.todoAppender = todoAppender;
        this.todoFinder = todoFinder;
        this.joinedMoimFinder = joinedMoimFinder;
    }

    @Override
    public void createTodo(final long memberId, final TodoCreateRequest todoCreateRequest) {
        joinedMoimFinder.validateMemberInMoim(todoCreateRequest.moimId(), memberId);

        todoAppender.createTodo(memberId, todoCreateRequest);
    }

    @Override
    public List<TodoResponse> findAll(final long memberId, final long moimId) {
        return todoFinder.findAll(memberId, moimId);
    }
}
