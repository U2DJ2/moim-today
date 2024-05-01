package moim_today.persistence.repository.todo;

import moim_today.persistence.entity.todo.TodoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoJpaRepository extends JpaRepository<TodoJpaEntity, Long> {

    void deleteAllByMoimId(final long moimId);
}
