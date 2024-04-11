package moim_today.persistence.entity.chat.single.joined_single_chat_room;

import moim_today.global.annotation.Association;
import moim_today.global.base_entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Table(name = "joined_single_chat_room")
@Entity
public class JoinedSingleChatRoomJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "joined_single_chat_room_id")
    private long id;

    @Association
    private long singleChatRoomId;

    @Association
    private long memberId;

    protected JoinedSingleChatRoomJpaEntity() {
    }

    @Builder
    private JoinedSingleChatRoomJpaEntity(final long singleChatRoomId, final long memberId) {
        this.singleChatRoomId = singleChatRoomId;
        this.memberId = memberId;
    }
}
