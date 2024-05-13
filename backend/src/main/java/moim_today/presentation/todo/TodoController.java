package moim_today.presentation.todo;

import moim_today.application.todo.TodoService;
import moim_today.domain.member.MemberSession;
import moim_today.dto.todo.*;
import moim_today.global.annotation.Login;
import moim_today.global.response.CollectionResponse;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;

@RequestMapping("/api/todos")
@RestController
public class TodoController {

    private final TodoService todoService;

    public TodoController(final TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public void createTodo(final @Login MemberSession memberSession,
                      final TodoCreateRequest todoCreateRequest){
        todoService.createTodo(memberSession.id(), todoCreateRequest);
    }

    @GetMapping("/{moimId}")
    public CollectionResponse<List<MemberTodoResponse>> findAllMembersTodosInMoim(
            final @Login MemberSession memberSession,
            final @PathVariable long moimId,
            final @RequestParam YearMonth startDate,
            final @RequestParam int months) {

        return CollectionResponse.of(todoService.findAllMembersTodosInMoim(memberSession.id(), moimId, startDate, months));
    }

    @PatchMapping
    public TodoUpdateResponse updateTodo(final @Login MemberSession memberSession,
                                         final TodoUpdateRequest todoUpdateRequest) {
        return todoService.updateTodo(memberSession.id(), todoUpdateRequest);
    }

    @DeleteMapping
    public void deleteTodo(final @Login MemberSession memberSession,
                           final TodoRemoveRequest todoRemoveRequest) {
        todoService.deleteTodo(memberSession.id(), todoRemoveRequest);
    }
}
