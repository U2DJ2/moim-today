package booki_today.persistence.entity.chat.regular.regular_chat_room;

import booki_today.global.annotation.Association;
import booki_today.global.base_entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Table(name = "regular_chat_room")
@Entity
public class RegularChatRoomJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "regular_chat_room_id")
    private long id;

    @Association
    private long regularMoimId;

    private String title;

    protected RegularChatRoomJpaEntity() {
    }

    @Builder
    private RegularChatRoomJpaEntity(final long regularMoimId, final String title) {
        this.regularMoimId = regularMoimId;
        this.title = title;
    }
}
