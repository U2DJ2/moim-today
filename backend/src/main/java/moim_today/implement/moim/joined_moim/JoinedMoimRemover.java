package moim_today.implement.moim.joined_moim;

import moim_today.global.annotation.Implement;
import moim_today.persistence.repository.moim.joined_moim.JoinedMoimRepository;
import org.springframework.transaction.annotation.Transactional;

@Implement
public class JoinedMoimRemover {

    private final JoinedMoimRepository joinedMoimRepository;

    public JoinedMoimRemover(final JoinedMoimRepository joinedMoimRepository) {
        this.joinedMoimRepository = joinedMoimRepository;
    }

    @Transactional
    public void deleteAllByMoimId(final long moimId) {
        joinedMoimRepository.deleteAllByMoimId(moimId);
    }

    @Transactional
    public void deleteMoimMember(final long moimId, final long memberId) {
        joinedMoimRepository.deleteMoimMember(moimId, memberId);
    }
}
