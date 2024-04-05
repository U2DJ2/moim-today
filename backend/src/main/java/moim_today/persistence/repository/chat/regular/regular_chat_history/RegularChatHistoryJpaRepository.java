package moim_today.persistence.repository.chat.regular.regular_chat_history;

import moim_today.persistence.entity.chat.regular.regular_chat_history.RegularChatHistoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegularChatHistoryJpaRepository extends JpaRepository<RegularChatHistoryJpaEntity, Long> {
}
