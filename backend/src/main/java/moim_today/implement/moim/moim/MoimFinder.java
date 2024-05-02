package moim_today.implement.moim.moim;

import moim_today.dto.moim.moim.MoimMemberResponse;
import moim_today.global.annotation.Implement;
import moim_today.implement.member.MemberFinder;
import moim_today.implement.moim.joined_moim.JoinedMoimFinder;
import moim_today.persistence.entity.member.MemberJpaEntity;
import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.persistence.repository.moim.moim.MoimRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Implement
public class MoimFinder {

    private final MoimRepository moimRepository;
    private final JoinedMoimFinder joinedMoimFinder;
    private final MemberFinder memberFinder;

    public MoimFinder(final MoimRepository moimRepository, final JoinedMoimFinder joinedMoimFinder, final MemberFinder memberFinder) {
        this.moimRepository = moimRepository;
        this.joinedMoimFinder = joinedMoimFinder;
        this.memberFinder = memberFinder;
    }

    @Transactional(readOnly = true)
    public List<JoinedMoimJpaEntity> findJoinedMoims(final long moimId) {
        return joinedMoimFinder.findMembersByMoimId(moimId);
    }

    @Transactional(readOnly = true)
    public MoimJpaEntity getById(final long moimId) {
        return moimRepository.getById(moimId);
    }

    @Transactional(readOnly = true)
    public List<MoimMemberResponse> findMoimMembers(final long moimId, final List<JoinedMoimJpaEntity> joinedMoimJpaEntities) {
        return joinedMoimJpaEntities.stream().map(joinedMoimJpaEntity -> {
            MemberJpaEntity memberJpaEntity = memberFinder.getMemberById(joinedMoimJpaEntity.getMemberId());
            boolean isHost = isHost(moimId, memberJpaEntity.getId());
            return MoimMemberResponse.of(memberJpaEntity, isHost, joinedMoimJpaEntity.getCreatedAt());
        }).toList();
    }

    @Transactional(readOnly = true)
    public boolean isHost(final long moimId, final long memberId) {
        MoimJpaEntity moimJpaEntity = getById(moimId);
        return moimJpaEntity.getMemberId() == memberId;
    }
}
