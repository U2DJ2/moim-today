package moim_today.application.todo;

import moim_today.dto.todo.MemberTodoResponse;
import moim_today.dto.todo.TodoCreateRequest;
import moim_today.dto.todo.TodoUpdateRequest;

import java.time.YearMonth;
import java.util.List;

public interface TodoService {

    void createTodo(final long memberId, final TodoCreateRequest todoCreateRequest);

    List<MemberTodoResponse> findAllMembersTodosInMoim(final long memberId,
                                                       final long moimId,
                                                       final YearMonth startDate,
                                                       final int months);

    void updateTodo(final long memberId, final TodoUpdateRequest todoUpdateRequest);
}
