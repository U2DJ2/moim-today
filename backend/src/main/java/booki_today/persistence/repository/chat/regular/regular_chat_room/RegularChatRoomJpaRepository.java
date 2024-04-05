package booki_today.persistence.repository.chat.regular.regular_chat_room;

import booki_today.persistence.entity.chat.regular.regular_chat_room.RegularChatRoomJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegularChatRoomJpaRepository extends JpaRepository<RegularChatRoomJpaEntity, Long> {
}
