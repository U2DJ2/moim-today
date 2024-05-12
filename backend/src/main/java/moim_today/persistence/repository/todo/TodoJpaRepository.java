package moim_today.persistence.repository.todo;

import moim_today.persistence.entity.todo.TodoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoJpaRepository extends JpaRepository<TodoJpaEntity, Long> {

    void deleteAllByMoimId(final long moimId);

    void deleteAllByMoimIdAndMemberId(final long moimId, final long memberId);

    List<TodoJpaEntity> findAllByMemberId(final long memberId);

    List<TodoJpaEntity> findAllByMemberIdAndMoimId(final long memberId, final long moimId);
}
