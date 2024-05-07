package moim_today.implement.moim.moim;

import moim_today.dto.moim.moim.MoimDateResponse;
import moim_today.dto.moim.moim.MoimMemberResponse;
import moim_today.dto.moim.moim.MoimSimpleResponse;
import moim_today.dto.moim.moim.filter.MoimFilterRequest;
import moim_today.global.annotation.Implement;
import moim_today.global.error.BadRequestException;
import moim_today.implement.member.MemberFinder;
import moim_today.implement.moim.joined_moim.JoinedMoimFinder;
import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.persistence.repository.moim.moim.MoimRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static moim_today.global.constant.exception.MoimExceptionConstant.MOIM_CAPACITY_ERROR;

@Implement
public class MoimFinder {

    private final JoinedMoimFinder joinedMoimFinder;
    private final MemberFinder memberFinder;
    private final MoimRepository moimRepository;

    public MoimFinder(final JoinedMoimFinder joinedMoimFinder,
                      final MemberFinder memberFinder,
                      final MoimRepository moimRepository) {
        this.joinedMoimFinder = joinedMoimFinder;
        this.memberFinder = memberFinder;
        this.moimRepository = moimRepository;
    }

    @Transactional(readOnly = true)
    public List<JoinedMoimJpaEntity> findJoinedMoims(final long moimId) {
        return joinedMoimFinder.findByMoimId(moimId);
    }

    @Transactional(readOnly = true)
    public MoimJpaEntity getById(final long moimId) {
        return moimRepository.getById(moimId);
    }

    @Transactional
    public MoimJpaEntity getByIdWithPessimisticLock(final long moimId){
        return moimRepository.getByIdWithPessimisticLock(moimId);
    }

    @Transactional(readOnly = true)
    public List<MoimMemberResponse> findMembersInMoim(final List<JoinedMoimJpaEntity> joinedMoimJpaEntities,
                                                      final long hostId) {
        List<Long> memberIds = extractMemberIds(joinedMoimJpaEntities);
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

    @Transactional(readOnly = true)
    public List<MoimSimpleResponse> findAllMoimResponse(final MoimFilterRequest moimFilterRequest) {
        return moimRepository.findAllMoimResponse(moimFilterRequest);
    }

    @Transactional(readOnly = true)
    public boolean isHost(final long memberId, final long moimId) {
        MoimJpaEntity moimJpaEntity = moimRepository.getById(moimId);
        return moimJpaEntity.getMemberId() == memberId;
    }

    @Transactional(readOnly = true)
    public void validateCapacity(final MoimJpaEntity moimJpaEntity){
        long moimId = moimJpaEntity.getId();
        List<Long> participatingMemberIds = joinedMoimFinder.findAllJoinedMemberId(moimId);
        if(participatingMemberIds.size() >= moimJpaEntity.getCapacity()){
            throw new BadRequestException(MOIM_CAPACITY_ERROR.message());
        }
    }

    private List<Long> extractMemberIds(final List<JoinedMoimJpaEntity> joinedMoimJpaEntities){
        List<Long> memberIds = new ArrayList<>();
        joinedMoimJpaEntities.forEach(e -> memberIds.add(e.getMemberId()));
        return memberIds;
    }
}
