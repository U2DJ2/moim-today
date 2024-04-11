package moim_today.persistence.repository.chat.single.single_chat_history;

import org.springframework.stereotype.Repository;

@Repository
public class SingleChatHistoryRepositoryImpl implements SingleChatHistoryRepository {

    private final SingleChatHistoryJpaRepository singleChatHistoryJpaRepository;

    public SingleChatHistoryRepositoryImpl(final SingleChatHistoryJpaRepository singleChatHistoryJpaRepository) {
        this.singleChatHistoryJpaRepository = singleChatHistoryJpaRepository;
    }
}
