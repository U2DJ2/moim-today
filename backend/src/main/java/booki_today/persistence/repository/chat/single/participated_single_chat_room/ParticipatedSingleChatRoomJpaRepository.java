package booki_today.persistence.repository.chat.single.participated_single_chat_room;

import booki_today.persistence.entity.chat.single.participated_single_chat_room.ParticipatedSingleChatRoomJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipatedSingleChatRoomJpaRepository extends JpaRepository<ParticipatedSingleChatRoomJpaEntity, Long> {
}
