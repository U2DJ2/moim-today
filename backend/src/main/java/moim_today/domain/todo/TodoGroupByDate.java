package moim_today.domain.todo;

import moim_today.dto.todo.TodoResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record TodoGroupByDate(
        LocalDate todoDate,
        List<TodoContents> todoContents
) {

    private static Map<LocalDate, List<TodoResponse>> groupedByDate(final List<TodoResponse> todos) {
        return todos.stream()
                .collect(Collectors.groupingBy(TodoResponse::todoDate));
    }

    public static List<TodoGroupByDate> todoGroupByDates(final List<TodoResponse> todos){
        Map<LocalDate, List<TodoResponse>> todoGroupByDates = groupedByDate(todos);
        return todoGroupByDates.entrySet().stream()
                .map(entry -> new TodoGroupByDate(
                        entry.getKey(),
                        entry.getValue().stream()
                                .map(todo -> new TodoContents(todo.todoId(), todo.contents(), todo.todoProgress()))
                                .toList()
                ))
                .toList();
    }
}
