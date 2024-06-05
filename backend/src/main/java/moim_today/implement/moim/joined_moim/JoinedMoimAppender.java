package moim_today.implement.moim.joined_moim;

import moim_today.global.annotation.Implement;
import moim_today.implement.moim.moim.MoimFinder;
import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;
import moim_today.persistence.repository.moim.joined_moim.JoinedMoimRepository;
import org.springframework.transaction.annotation.Transactional;

@Implement
public class JoinedMoimAppender {

    private final JoinedMoimRepository joinedMoimRepository;

    public JoinedMoimAppender(final JoinedMoimRepository joinedMoimRepository) {
        this.joinedMoimRepository = joinedMoimRepository;
    }

    @Transactional
    public void createJoinedMoim(final long memberId, final long moimId) {
        JoinedMoimJpaEntity joinedMoimJpaEntity = JoinedMoimJpaEntity.of(memberId, moimId);
        joinedMoimRepository.save(joinedMoimJpaEntity);
    }
}
