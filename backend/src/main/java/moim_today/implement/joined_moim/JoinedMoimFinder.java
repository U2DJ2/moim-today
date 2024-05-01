package moim_today.implement.joined_moim;

import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;
import moim_today.persistence.repository.moim.joined_moim.JoinedMoimRepository;

import java.util.List;

@Implement
public class JoinedMoimFinder {

    private final JoinedMoimRepository joinedMoimRepository;

    public JoinedMoimFinder(JoinedMoimRepository joinedMoimRepository) {
        this.joinedMoimRepository = joinedMoimRepository;
    }

    public List<JoinedMoimJpaEntity> findMembersByMoimId(final long moimId) {
        return null;
    }
}
