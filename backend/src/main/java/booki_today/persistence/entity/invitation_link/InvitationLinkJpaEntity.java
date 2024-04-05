package booki_today.persistence.entity.invitation_link;

import booki_today.global.annotation.Association;
import booki_today.global.base_entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Table(name = "invitaion_link")
@Entity
public class InvitationLinkJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invitation_link_id")
    private long id;

    @Association
    private long regularMoimId;

    private String invitationLink;

    private LocalDateTime expiredDateTime;

    protected InvitationLinkJpaEntity() {
    }

    @Builder
    private InvitationLinkJpaEntity(final long regularMoimId, final String invitationLink,
                                    final LocalDateTime expiredDateTime) {
        this.regularMoimId = regularMoimId;
        this.invitationLink = invitationLink;
        this.expiredDateTime = expiredDateTime;
    }
}