package moim_today.presentation.todo;

import moim_today.application.todo.TodoService;
import moim_today.domain.member.MemberSession;
import moim_today.dto.todo.TodoCreateRequest;
import moim_today.global.annotation.Login;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("")
}
