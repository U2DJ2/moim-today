package booki_today.persistence.repository.chat.single.participated_single_chat_room;

import org.springframework.stereotype.Repository;

@Repository
public class ParticipatedSingleChatRoomRepositoryImpl implements ParticipatedSingleChatRoomRepository {

    private final ParticipatedSingleChatRoomJpaRepository participatedSingleChatRoomJpaRepository;

    public ParticipatedSingleChatRoomRepositoryImpl(final ParticipatedSingleChatRoomJpaRepository participatedSingleChatRoomJpaRepository) {
        this.participatedSingleChatRoomJpaRepository = participatedSingleChatRoomJpaRepository;
    }
}
