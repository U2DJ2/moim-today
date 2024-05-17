package moim_today.implement.moim.joined_moim;

import moim_today.global.annotation.Implement;
import moim_today.implement.moim.moim.MoimFinder;
import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.persistence.repository.moim.joined_moim.JoinedMoimRepository;
import org.springframework.transaction.annotation.Transactional;

@Implement
public class JoinedMoimAppender {

    private final JoinedMoimRepository joinedMoimRepository;
    private final MoimFinder moimFinder;

    public JoinedMoimAppender(final JoinedMoimRepository joinedMoimRepository,
                              final MoimFinder moimFinder) {
        this.joinedMoimRepository = joinedMoimRepository;
        this.moimFinder = moimFinder;
    }

    @Transactional
    public void createJoinedMoim(final long memberId, final long moimId) {
        MoimJpaEntity findMoim = moimFinder.getByIdWithPessimisticLock(moimId);
        findMoim.updateCurrentCount(1);

        JoinedMoimJpaEntity joinedMoimJpaEntity = JoinedMoimJpaEntity.of(memberId, moimId);

        joinedMoimRepository.save(joinedMoimJpaEntity);
    }
}
