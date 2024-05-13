package moim_today.presentation.todo;

import jakarta.validation.Valid;
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
    public TodoCreateResponse createTodo(final @Login MemberSession memberSession,
                      final @RequestBody @Valid TodoCreateRequest todoCreateRequest){
        return todoService.createTodo(memberSession.id(), todoCreateRequest);
    }


    @GetMapping("moim/{moimId}")
    public CollectionResponse<List<MemberTodoResponse>> findAllMembersTodosInMoim(
            final @Login MemberSession memberSession,
            final @PathVariable long moimId,
            final @RequestParam YearMonth startDate,
            final @RequestParam int months) {

        return CollectionResponse.of(todoService.findAllMembersTodosInMoim(memberSession.id(), moimId, startDate, months));
    }

    @GetMapping("/{todoId}")
    public TodoDetailResponse findTodoById(final @PathVariable long todoId){
        return todoService.getById(todoId);
    }

    @PatchMapping
    public TodoUpdateResponse updateTodo(final @Login MemberSession memberSession,
                                         final @RequestBody TodoUpdateRequest todoUpdateRequest) {
        return todoService.updateTodo(memberSession.id(), todoUpdateRequest);
    }

    @DeleteMapping
    public void deleteTodo(final @Login MemberSession memberSession,
                           final @RequestBody TodoRemoveRequest todoRemoveRequest) {
        todoService.deleteTodo(memberSession.id(), todoRemoveRequest);
    }
}
