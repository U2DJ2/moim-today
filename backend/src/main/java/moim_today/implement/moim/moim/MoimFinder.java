package moim_today.implement.moim.moim;

import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.persistence.repository.moim.moim.MoimRepository;
import org.springframework.transaction.annotation.Transactional;
import moim_today.persistence.entity.member.MemberJpaEntity;
import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;
import moim_today.persistence.repository.member.MemberRepository;
import moim_today.persistence.repository.moim.joined_moim.JoinedMoimRepository;

import java.util.List;

@Implement
public class MoimFinder {

    private final JoinedMoimRepository joinedMoimRepository;
    private final MemberRepository memberRepository;
    private final MoimRepository moimRepository;

    public MoimFinder(final JoinedMoimRepository joinedMoimRepository, final MemberRepository memberRepository, final MoimRepository moimRepository1) {
        this.joinedMoimRepository = joinedMoimRepository;
        this.memberRepository = memberRepository;
        this.moimRepository = moimRepository1;
    }

    public List<MemberJpaEntity> findMoimMembers(final long moimId) {
        List<JoinedMoimJpaEntity> joinedMoimJpaEntities = joinedMoimRepository.findMembersByMoimId(moimId);
        List<MemberJpaEntity> memberJpaEntities = joinedMoimJpaEntities.stream().map(joinedMoimJpaEntity ->
                memberRepository.getById(joinedMoimJpaEntity.getMemberId()))
                .toList();
        return memberJpaEntities;
    }

    @Transactional(readOnly = true)
    public MoimJpaEntity getById(final long moimId) {
        return moimRepository.getById(moimId);
    }
}
