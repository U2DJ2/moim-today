package booki_today.persistence.entity.invitation_link;

import booki_today.global.annotation.Association;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Table(name = "invitaion_link")
@Entity
public class InvitationLinkJpaEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invitation_link_id")
    private Long id;

    @Association
    private Long createdBookiId;

    private String invitationLink;

    private LocalDateTime linkExpiredDateTime;

    protected InvitationLinkJpaEntity() {
    }

    @Builder
    private InvitationLinkJpaEntity(final Long createdBookiId, final String invitationLink,
                                    final LocalDateTime linkExpiredDateTime) {
        this.createdBookiId = createdBookiId;
        this.invitationLink = invitationLink;
        this.linkExpiredDateTime = linkExpiredDateTime;
    }
}
