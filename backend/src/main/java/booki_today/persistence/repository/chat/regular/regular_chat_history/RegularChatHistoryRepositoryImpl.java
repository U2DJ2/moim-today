package booki_today.persistence.repository.chat.regular.regular_chat_history;

import org.springframework.stereotype.Repository;

@Repository
public class RegularChatHistoryRepositoryImpl implements RegularChatHistoryRepository {

    private final RegularChatHistoryJpaRepository regularChatHistoryJpaRepository;

    public RegularChatHistoryRepositoryImpl(final RegularChatHistoryJpaRepository regularChatHistoryJpaRepository) {
        this.regularChatHistoryJpaRepository = regularChatHistoryJpaRepository;
    }
}
