package booki_today.persistence.entity.chat.regular.participated_regular_chat_room;

import booki_today.global.annotation.Association;
import booki_today.global.base_entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Table(name = "participated_regular_chat_room")
@Entity
public class ParticipatedRegularChatRoomJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participated_regular_chat_room_id")
    private long id;

    @Association
    private long regularChatRoomId;

    @Association
    private long memberId;

    public ParticipatedRegularChatRoomJpaEntity() {
    }

    @Builder
    private ParticipatedRegularChatRoomJpaEntity(final long regularChatRoomId, final long memberId) {
        this.regularChatRoomId = regularChatRoomId;
        this.memberId = memberId;
    }
}
