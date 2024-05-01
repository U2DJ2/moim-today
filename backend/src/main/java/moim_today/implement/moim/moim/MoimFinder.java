package moim_today.implement.moim.moim;

import moim_today.domain.moim.MoimMember;
import moim_today.global.annotation.Implement;
import moim_today.implement.joined_moim.JoinedMoimFinder;
import moim_today.implement.member.MemberFinder;
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

    public MoimFinder(final MoimRepository moimRepository1, final JoinedMoimFinder joinedMoimFinder
            , final MemberFinder memberFinder) {
        this.moimRepository = moimRepository1;
        this.joinedMoimFinder = joinedMoimFinder;
        this.memberFinder = memberFinder;
    }

    @Transactional
    public List<MoimMember> findMoimMembers(final long moimId) {
        List<JoinedMoimJpaEntity> joinedMoimJpaEntities = joinedMoimFinder.findMembersByMoimId(moimId);
        List<MoimMember> memberJpaEntities = joinedMoimJpaEntities.stream().map(joinedMoimJpaEntity ->
                memberFinder.getMoimMemberById(joinedMoimJpaEntity.getMemberId()))
                .toList();
        return memberJpaEntities;
    }

    @Transactional(readOnly = true)
    public MoimJpaEntity getById(final long moimId) {
        return moimRepository.getById(moimId);
    }
}
