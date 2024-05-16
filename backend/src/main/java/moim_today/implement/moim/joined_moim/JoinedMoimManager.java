package moim_today.implement.moim.joined_moim;

import moim_today.global.annotation.Implement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Implement
public class JoinedMoimManager {

    private final JoinedMoimFinder joinedMoimFinder;

    public JoinedMoimManager(JoinedMoimFinder joinedMoimFinder) {
        this.joinedMoimFinder = joinedMoimFinder;
    }

    @Transactional(readOnly = true)
    public List<Long> findAllJoinedMemberId(final long moimId) {
        return joinedMoimFinder.findAllJoinedMemberId(moimId);
    }

    public List<Long> findMoimIdsByMemberId(final long memberId) {
        return joinedMoimFinder.findMoimIdsByMemberId(memberId);
    }
}
