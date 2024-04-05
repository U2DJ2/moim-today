package booki_today.persistence.repository.chat.single.joined_single_chat_room;

import org.springframework.stereotype.Repository;

@Repository
public class JoinedSingleChatRoomRepositoryImpl implements JoinedSingleChatRoomRepository {

    private final JoinedSingleChatRoomJpaRepository joinedSingleChatRoomJpaRepository;

    public JoinedSingleChatRoomRepositoryImpl(final JoinedSingleChatRoomJpaRepository joinedSingleChatRoomJpaRepository) {
        this.joinedSingleChatRoomJpaRepository = joinedSingleChatRoomJpaRepository;
    }
}
