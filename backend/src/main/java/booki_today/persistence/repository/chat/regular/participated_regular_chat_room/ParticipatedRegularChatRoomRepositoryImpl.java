package booki_today.persistence.repository.chat.regular.participated_regular_chat_room;

import org.springframework.stereotype.Repository;

@Repository
public class ParticipatedRegularChatRoomRepositoryImpl implements ParticipatedRegularChatRoomRepository {

    private final ParticipatedRegularChatRoomJpaRepository participatedRegularChatRoomJpaRepository;

    public ParticipatedRegularChatRoomRepositoryImpl(final ParticipatedRegularChatRoomJpaRepository participatedRegularChatRoomJpaRepository) {
        this.participatedRegularChatRoomJpaRepository = participatedRegularChatRoomJpaRepository;
    }
}
