package booki_today.persistence.entity.chat.single.participated_single_chat_room;

import booki_today.global.annotation.Association;
import booki_today.global.base_entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Table(name = "participated_single_chat_room")
@Entity
public class ParticipatedSingleChatRoomJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participated_single_chat_room_id")
    private long id;

    @Association
    private long singleChatRoomId;

    @Association
    private long memberId;

    protected ParticipatedSingleChatRoomJpaEntity() {
    }

    @Builder
    private ParticipatedSingleChatRoomJpaEntity(final long singleChatRoomId, final long memberId) {
        this.singleChatRoomId = singleChatRoomId;
        this.memberId = memberId;
    }
}
