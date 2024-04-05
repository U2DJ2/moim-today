package booki_today.persistence.entity.chat.regular.regular_chat_history;

import booki_today.global.annotation.Association;
import booki_today.global.base_entity.CreatedTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;


@Getter
@Table(name = "regular_chat_history")
@Entity
public class RegularChatHistoryJpaEntity extends CreatedTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "regular_chat_history_id")
    private long id;

    @Association
    private long regularChatRoomId;

    @Association
    private long memberId;

    private String contents;

    protected RegularChatHistoryJpaEntity() {
    }

    @Builder
    private RegularChatHistoryJpaEntity(final long regularChatRoomId, final long memberId, final String contents) {
        this.regularChatRoomId = regularChatRoomId;
        this.memberId = memberId;
        this.contents = contents;
    }
}
