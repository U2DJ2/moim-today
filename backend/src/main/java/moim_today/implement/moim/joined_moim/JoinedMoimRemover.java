package moim_today.implement.moim.joined_moim;

import moim_today.global.annotation.Implement;
import moim_today.persistence.repository.moim.joined_moim.JoinedMoimRepository;

@Implement
public class JoinedMoimRemover {

    private final JoinedMoimRepository joinedMoimRepository;

    public JoinedMoimRemover(final JoinedMoimRepository joinedMoimRepository) {
        this.joinedMoimRepository = joinedMoimRepository;
    }

    public void deleteAllByMoimId(final long moimId) {
        joinedMoimRepository.deleteAllByMoimId(moimId);
    }
}
