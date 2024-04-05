package booki_today.persistence.repository.chat.single.joined_single_chat_room;

import booki_today.persistence.entity.chat.single.joined_single_chat_room.JoinedSingleChatRoomJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JoinedSingleChatRoomJpaRepository extends JpaRepository<JoinedSingleChatRoomJpaEntity, Long> {
}
