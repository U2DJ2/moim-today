package moim_today.implement.todo;

import moim_today.domain.todo.enums.TodoProgress;
import moim_today.dto.todo.*;
import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.todo.TodoJpaEntity;

import java.time.YearMonth;
import java.util.List;


@Implement
public class TodoComposition {

    private final TodoAppender todoAppender;
    private final TodoFinder todoFinder;
    private final TodoUpdater todoUpdater;
    private final TodoRemover todoRemover;

    public TodoComposition(final TodoAppender todoAppender, final TodoFinder todoFinder,
                           final TodoUpdater todoUpdater, final TodoRemover todoRemover) {
        this.todoAppender = todoAppender;
        this.todoFinder = todoFinder;
        this.todoUpdater = todoUpdater;
        this.todoRemover = todoRemover;
    }

    public TodoJpaEntity createTodo(final long memberId, final TodoCreateRequest todoCreateRequest){
        return todoAppender.createTodo(memberId, todoCreateRequest);
    }

    public List<TodoResponse> findMemberTodosInMoim(final long memberId, final Long moimId,
                                                    final YearMonth fromDate, final int months) {
        return todoFinder.findMemberTodosInMoim(memberId, moimId, fromDate, months);
    }

    public List<MemberTodoResponse> findAllMembersTodosInMoim(final long moimId,
                                                              final YearMonth fromDate,
                                                              final int months) {
        return todoFinder.findAllMembersTodosInMoim(moimId, fromDate, months);
    }

    public TodoJpaEntity getById(final long todoId){
        return todoFinder.getById(todoId);
    }

    public TodoUpdateResponse updateTodo(final long memberId, final TodoUpdateRequest todoUpdateRequest) {
        return todoUpdater.updateTodo(memberId, todoUpdateRequest);
    }

    public TodoUpdateResponse updateTodoProgress(final long memberId, final long todoId, final TodoProgress todoProgress) {
        return todoUpdater.updateTodoProgress(memberId, todoId, todoProgress);
    }

    public void deleteAllByMoimId(final long moimId) {
        todoRemover.deleteAllByMoimId(moimId);
    }

    public void deleteById(final long todoId) {
        todoRemover.deleteById(todoId);
    }

    public void deleteTodo(final long memberId, final TodoRemoveRequest todoRemoveRequest) {
        todoRemover.deleteTodo(memberId, todoRemoveRequest);
    }
}
