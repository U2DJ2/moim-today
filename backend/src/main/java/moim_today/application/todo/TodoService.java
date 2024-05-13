package moim_today.application.todo;

import moim_today.dto.todo.*;

import java.time.YearMonth;
import java.util.List;

public interface TodoService {

    TodoCreateResponse createTodo(final long memberId, final TodoCreateRequest todoCreateRequest);

    List<MemberTodoResponse> findAllMembersTodosInMoim(final long memberId,
                                                       final long moimId,
                                                       final YearMonth startDate,
                                                       final int months);

    TodoUpdateResponse updateTodo(final long memberId, final TodoUpdateRequest todoUpdateRequest);

    void deleteTodo(final long id, final TodoRemoveRequest todoRemoveRequest);

    TodoDetailResponse getById(final long todoId);
}
