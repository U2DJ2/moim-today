package moim_today.fake_class.todo;

import moim_today.application.todo.TodoService;
import moim_today.dto.todo.*;

import java.time.YearMonth;
import java.util.List;

public class FakeTodoService implements TodoService {

    @Override
    public void createTodo(final long memberId, final TodoCreateRequest todoCreateRequest) {

    }

    @Override
    public List<MemberTodoResponse> findAllMembersTodosInMoim(final long memberId, final long moimId,
                                                              final YearMonth startDate, final int months) {
        return null;
    }

    @Override
    public TodoUpdateResponse updateTodo(final long memberId, final TodoUpdateRequest todoUpdateRequest) {
        return null;
    }

    @Override
    public void deleteTodo(final long id, final TodoRemoveRequest todoRemoveRequest) {

    }
}
