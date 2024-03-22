package booki_today.persistence.entity.invitation_link;

import booki_today.global.annotation.Association;
import booki_today.persistence.entity.BaseTimeEntity;
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
    private long assembleId;

    private String invitationLink;

    private LocalDateTime linkExpiredDateTime;

    protected InvitationLinkJpaEntity() {
    }

    @Builder
    private InvitationLinkJpaEntity(final long assembleId, final String invitationLink,
                                    final LocalDateTime linkExpiredDateTime) {
        this.assembleId = assembleId;
        this.invitationLink = invitationLink;
        this.linkExpiredDateTime = linkExpiredDateTime;
    }
}
