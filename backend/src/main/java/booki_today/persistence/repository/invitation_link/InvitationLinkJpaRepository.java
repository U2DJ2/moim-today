package booki_today.persistence.repository.invitation_link;

import booki_today.persistence.entity.invitation_link.InvitationLinkJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationLinkJpaRepository extends JpaRepository<InvitationLinkJpaEntity, Long> {
}
