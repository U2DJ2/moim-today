package moim_today.persistence.repository.chat.regular.regular_chat_room;

import moim_today.persistence.entity.chat.regular.regular_chat_room.RegularChatRoomJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegularChatRoomJpaRepository extends JpaRepository<RegularChatRoomJpaEntity, Long> {
}
