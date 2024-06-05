package moim_today.implement.moim.joined_moim;

import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;

import java.util.List;


@Implement
public class JoinedMoimComposition {

    private final JoinedMoimAppender joinedMoimAppender;
    private final JoinedMoimFinder joinedMoimFinder;
    private final JoinedMoimRemover joinedMoimRemover;

    public JoinedMoimComposition(final JoinedMoimAppender joinedMoimAppender,
                                 final JoinedMoimFinder joinedMoimFinder,
                                 final JoinedMoimRemover joinedMoimRemover) {
        this.joinedMoimAppender = joinedMoimAppender;
        this.joinedMoimFinder = joinedMoimFinder;
        this.joinedMoimRemover = joinedMoimRemover;
    }

    public void createJoinedMoim(final long memberId, final long moimId) {
        joinedMoimAppender.createJoinedMoim(memberId, moimId);
    }

    public List<JoinedMoimJpaEntity> findByMoimId(final long moimId) {
        return joinedMoimFinder.findByMoimId(moimId);
    }

    public List<Long> findAllJoinedMemberId(final long moimId) {
        return joinedMoimFinder.findAllJoinedMemberId(moimId);
    }

    public void validateMemberInMoim(final long memberId, final long moimId) {
        joinedMoimFinder.validateMemberInMoim(memberId, moimId);
    }

    public boolean isJoining(final long moimId, final long memberId) {
        return joinedMoimFinder.isJoining(moimId, memberId);
    }

    public List<Long> findMoimIdsByMemberId(final long memberId) {
        return joinedMoimFinder.findMoimIdsByMemberId(memberId);
    }

    public void deleteAllByMoimId(final long moimId) {
        joinedMoimRemover.deleteAllByMoimId(moimId);
    }

    public void deleteMoimMember(final long moimId, final long memberId) {
        joinedMoimRemover.deleteMoimMember(moimId, memberId);
    }
}
