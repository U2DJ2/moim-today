package moim_today.persistence.repository.todo;

public interface TodoRepository {
    void deleteAllByMoimId(final long moimId);
}
