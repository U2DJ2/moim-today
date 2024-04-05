package booki_today.persistence.repository.chat.single.single_chat_room;

import booki_today.persistence.entity.chat.single.single_chat_history.SingleChatHistoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SingleChatRoomJpaRepository extends JpaRepository<SingleChatHistoryJpaEntity, Long> {
}
