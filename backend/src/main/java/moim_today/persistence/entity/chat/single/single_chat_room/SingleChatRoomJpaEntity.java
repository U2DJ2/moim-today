package moim_today.persistence.entity.chat.single.single_chat_room;

import moim_today.global.annotation.Association;
import moim_today.global.base_entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Table(name = "single_chat_room")
@Entity
public class SingleChatRoomJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "single_chat_room_id")
    private long id;

    @Association
    private long singleMoimId;

    private String title;

    private LocalDateTime expiredDateTime;

    protected SingleChatRoomJpaEntity() {
    }

    @Builder
    private SingleChatRoomJpaEntity(final long singleMoimId, final String title, final LocalDateTime expiredDateTime) {
        this.singleMoimId = singleMoimId;
        this.title = title;
        this.expiredDateTime = expiredDateTime;
    }
}
