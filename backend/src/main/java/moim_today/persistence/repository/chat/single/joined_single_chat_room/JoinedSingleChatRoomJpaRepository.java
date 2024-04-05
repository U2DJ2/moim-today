package moim_today.persistence.repository.chat.single.joined_single_chat_room;

import moim_today.persistence.entity.chat.single.joined_single_chat_room.JoinedSingleChatRoomJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JoinedSingleChatRoomJpaRepository extends JpaRepository<JoinedSingleChatRoomJpaEntity, Long> {
}
