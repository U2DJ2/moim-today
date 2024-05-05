package moim_today.implement.moim.moim;

import moim_today.dto.moim.moim.MoimDateResponse;
import moim_today.dto.moim.moim.MoimMemberResponse;
import moim_today.global.annotation.Implement;
import moim_today.implement.member.MemberFinder;
import moim_today.implement.moim.joined_moim.JoinedMoimFinder;
import moim_today.implement.moim.joined_moim.JoinedMoimManager;
import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.persistence.repository.moim.moim.MoimRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Implement
public class MoimFinder {

    private final JoinedMoimManager joinedMoimManager;
    private final JoinedMoimFinder joinedMoimFinder;
    private final MoimRepository moimRepository;
    private final MemberFinder memberFinder;

    public MoimFinder(final JoinedMoimManager joinedMoimManager, final MoimRepository moimRepository,
                      final JoinedMoimFinder joinedMoimFinder, final MemberFinder memberFinder) {
        this.joinedMoimManager = joinedMoimManager;
        this.moimRepository = moimRepository;
        this.joinedMoimFinder = joinedMoimFinder;
        this.memberFinder = memberFinder;
    }

    @Transactional(readOnly = true)
    public List<JoinedMoimJpaEntity> findJoinedMoims(final long moimId) {
        return joinedMoimFinder.findByMoimId(moimId);
    }

    @Transactional(readOnly = true)
    public MoimJpaEntity getById(final long moimId) {
        return moimRepository.getById(moimId);
    }

    @Transactional(readOnly = true)
    public List<MoimMemberResponse> findMembersInMoim(final List<JoinedMoimJpaEntity> joinedMoimJpaEntities,
                                                      final long hostId) {
        List<Long> memberIds = joinedMoimManager.extractMemberIds(joinedMoimJpaEntities);
        return memberFinder.findMembersWithJoinedInfo(memberIds, hostId);
    }

    @Transactional(readOnly = true)
    public String getTitleById(final long moimId) {
        return moimRepository.getTitleById(moimId);
    }

    @Transactional(readOnly = true)
    public MoimDateResponse findMoimDate(final long moimId) {
        return moimRepository.findMoimDate(moimId);
    }

    public boolean isHost(final long moimHostId, final long memberId) {
        return moimHostId == memberId;
    }

    @Transactional(readOnly = true)
    public long getMemberIdById(final long moimId) {
        return moimRepository.getMemberIdById(moimId);
    }
}
