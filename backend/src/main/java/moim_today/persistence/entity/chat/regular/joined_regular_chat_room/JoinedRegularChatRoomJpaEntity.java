package moim_today.persistence.entity.chat.regular.joined_regular_chat_room;

import moim_today.global.annotation.Association;
import moim_today.global.base_entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Table(name = "joined_regular_chat_room")
@Entity
public class JoinedRegularChatRoomJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "joined_regular_chat_room_id")
    private long id;

    @Association
    private long regularChatRoomId;

    @Association
    private long memberId;

    public JoinedRegularChatRoomJpaEntity() {
    }

    @Builder
    private JoinedRegularChatRoomJpaEntity(final long regularChatRoomId, final long memberId) {
        this.regularChatRoomId = regularChatRoomId;
        this.memberId = memberId;
    }
}
