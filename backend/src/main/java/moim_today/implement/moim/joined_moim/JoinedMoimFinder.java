package moim_today.implement.moim.joined_moim;

import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;
import moim_today.persistence.repository.moim.joined_moim.JoinedMoimRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Implement
public class JoinedMoimFinder {

    private final JoinedMoimRepository joinedMoimRepository;

    public JoinedMoimFinder(final JoinedMoimRepository joinedMoimRepository) {
        this.joinedMoimRepository = joinedMoimRepository;
    }

    @Transactional(readOnly = true)
    public List<JoinedMoimJpaEntity> findByMoimId(final long moimId) {
        return joinedMoimRepository.findJoinMembersByMoimId(moimId);
    }
}
