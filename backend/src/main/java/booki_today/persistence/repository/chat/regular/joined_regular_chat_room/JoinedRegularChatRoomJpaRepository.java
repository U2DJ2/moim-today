package booki_today.persistence.repository.chat.regular.joined_regular_chat_room;

import booki_today.persistence.entity.chat.regular.joined_regular_chat_room.JoinedRegularChatRoomJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JoinedRegularChatRoomJpaRepository extends JpaRepository<JoinedRegularChatRoomJpaEntity, Long> {
}