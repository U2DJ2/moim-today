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
    public TodoCreateResponse createTodo(@Login final MemberSession memberSession,
                                         @RequestBody @Valid final TodoCreateRequest todoCreateRequest) {
        return todoService.createTodo(memberSession.id(), todoCreateRequest);
    }

    @GetMapping
    public CollectionResponse<List<MemberMoimTodoResponse>> findAllTodoByMemberId(
            @Login final MemberSession memberSession,
            @RequestParam final YearMonth requestDate,
            @RequestParam final int months){
        return CollectionResponse.from(todoService.findAllMemberTodos(memberSession.id(), requestDate, months));
    }

    @GetMapping("moim/{moimId}")
    public CollectionResponse<List<MemberTodoResponse>> findMembersTodosInMoim(
            @Login final MemberSession memberSession,
            @RequestParam(required = false, defaultValue = "0") final long memberId,
            @PathVariable final long moimId,
            @RequestParam final YearMonth requestDate,
            @RequestParam final int months) {
        return CollectionResponse.from(todoService.findMembersTodosInMoim(memberSession.id(), memberId, moimId, requestDate, months));
    }

    @GetMapping("/{todoId}")
    public TodoResponse findTodoById(@PathVariable final long todoId) {
        return todoService.getById(todoId);
    }

    @PatchMapping
    public TodoUpdateResponse updateTodo(@Login final MemberSession memberSession,
                                         @RequestBody @Valid final TodoUpdateRequest todoUpdateRequest) {
        return todoService.updateTodo(memberSession.id(), todoUpdateRequest);
    }

    @PatchMapping("/todo-progress")
    public TodoUpdateResponse updateTodoProgress(@Login final MemberSession memberSession,
                                                 @RequestBody @Valid final TodoProgressUpdateRequest todoProgressUpdateRequest){
        return todoService.updateTodoProgress(memberSession.id(), todoProgressUpdateRequest);
    }

    @DeleteMapping
    public void deleteTodo(@Login final MemberSession memberSession,
                           @RequestBody @Valid final TodoRemoveRequest todoRemoveRequest) {
        todoService.deleteTodo(memberSession.id(), todoRemoveRequest);
    }
}
