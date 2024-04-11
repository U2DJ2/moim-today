package moim_today.persistence.repository.chat.single.single_chat_room;

import org.springframework.stereotype.Repository;

@Repository
public class SingleChatRoomRepositoryImpl implements SingleChatRoomRepository {

    private final SingleChatRoomJpaRepository singleChatRoomJpaRepository;

    public SingleChatRoomRepositoryImpl(final SingleChatRoomJpaRepository singleChatRoomJpaRepository) {
        this.singleChatRoomJpaRepository = singleChatRoomJpaRepository;
    }
}
