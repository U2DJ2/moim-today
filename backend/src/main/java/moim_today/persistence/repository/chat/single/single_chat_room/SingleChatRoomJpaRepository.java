package moim_today.persistence.repository.chat.single.single_chat_room;

import moim_today.persistence.entity.chat.single.single_chat_room.SingleChatRoomJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SingleChatRoomJpaRepository extends JpaRepository<SingleChatRoomJpaEntity, Long> {
}
