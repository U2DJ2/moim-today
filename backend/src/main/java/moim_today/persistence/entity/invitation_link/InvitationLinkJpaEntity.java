package moim_today.persistence.entity.invitation_link;

import moim_today.global.annotation.Association;
import moim_today.global.base_entity.BaseTimeEntity;
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
    private long moimId;

    private String invitationLink;

    private LocalDateTime expiredDateTime;

    protected InvitationLinkJpaEntity() {
    }

    @Builder
    private InvitationLinkJpaEntity(final long moimId, final String invitationLink,
                                    final LocalDateTime expiredDateTime) {
        this.moimId = moimId;
        this.invitationLink = invitationLink;
        this.expiredDateTime = expiredDateTime;
    }
}