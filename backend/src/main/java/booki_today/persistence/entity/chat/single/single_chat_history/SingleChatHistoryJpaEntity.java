package booki_today.persistence.entity.chat.single.single_chat_history;

import booki_today.global.annotation.Association;
import booki_today.global.base_entity.CreatedTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Table(name = "single_chat_history")
@Entity
public class SingleChatHistoryJpaEntity extends CreatedTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "single_chat_history_id")
    private long id;

    @Association
    private long singleChatRoomId;

    @Association
    private long memberId;

    private String contents;

    protected SingleChatHistoryJpaEntity() {
    }

    @Builder
    private SingleChatHistoryJpaEntity(final long singleChatRoomId, final long memberId, final String contents) {
        this.singleChatRoomId = singleChatRoomId;
        this.memberId = memberId;
        this.contents = contents;
    }
}
