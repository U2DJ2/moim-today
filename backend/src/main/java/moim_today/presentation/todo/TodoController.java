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
            @RequestParam final YearMonth startDate,
            @RequestParam final int months){
        return CollectionResponse.from(todoService.findAllMembersTodos(memberSession.id(), startDate, months));
    }

    @GetMapping("moim/{moimId}")
    public CollectionResponse<List<MemberTodoResponse>> findAllMembersTodosInMoim(
            @Login final MemberSession memberSession,
            @PathVariable final long moimId,
            @RequestParam final YearMonth startDate,
            @RequestParam final int months) {

        return CollectionResponse.from(todoService.findAllMembersTodosInMoim(memberSession.id(), moimId, startDate, months));
    }

    @GetMapping("/{todoId}")
    public TodoResponse findTodoById(@PathVariable final long todoId) {
        return todoService.getById(todoId);
    }

    @PatchMapping
    public TodoUpdateResponse updateTodo(@Login final MemberSession memberSession,
                                         @RequestBody final TodoUpdateRequest todoUpdateRequest) {
        return todoService.updateTodo(memberSession.id(), todoUpdateRequest);
    }

    @DeleteMapping
    public void deleteTodo(@Login final MemberSession memberSession,
                           @RequestBody final TodoRemoveRequest todoRemoveRequest) {
        todoService.deleteTodo(memberSession.id(), todoRemoveRequest);
    }
}
