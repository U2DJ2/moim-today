package moim_today.persistence.repository.invitation_link;

public class InvitationLinkRepositoryImpl implements InvitationLinkRepository {

    private final InvitationLinkJpaRepository invitationLinkJpaRepository;

    public InvitationLinkRepositoryImpl(final InvitationLinkJpaRepository invitationLinkJpaRepository) {
        this.invitationLinkJpaRepository = invitationLinkJpaRepository;
    }
}
