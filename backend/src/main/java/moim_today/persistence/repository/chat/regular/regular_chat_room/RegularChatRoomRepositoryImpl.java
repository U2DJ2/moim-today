package moim_today.persistence.repository.chat.regular.regular_chat_room;

import org.springframework.stereotype.Repository;

@Repository
public class RegularChatRoomRepositoryImpl implements RegularChatRoomRepository {

    private final RegularChatRoomJpaRepository regularChatRoomJpaRepository;

    public RegularChatRoomRepositoryImpl(final RegularChatRoomJpaRepository regularChatRoomJpaRepository) {
        this.regularChatRoomJpaRepository = regularChatRoomJpaRepository;
    }
}
