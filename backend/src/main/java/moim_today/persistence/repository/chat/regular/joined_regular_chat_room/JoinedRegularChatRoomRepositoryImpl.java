package moim_today.persistence.repository.chat.regular.joined_regular_chat_room;

import org.springframework.stereotype.Repository;

@Repository
public class JoinedRegularChatRoomRepositoryImpl implements JoinedRegularChatRoomRepository {

    private final JoinedRegularChatRoomJpaRepository joinedRegularChatRoomJpaRepository;

    public JoinedRegularChatRoomRepositoryImpl(final JoinedRegularChatRoomJpaRepository joinedRegularChatRoomJpaRepository) {
        this.joinedRegularChatRoomJpaRepository = joinedRegularChatRoomJpaRepository;
    }
}
