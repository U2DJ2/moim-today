package moim_today.implement.moim.joined_moim;

import moim_today.global.annotation.Implement;
import moim_today.global.error.BadRequestException;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;
import moim_today.persistence.repository.moim.joined_moim.JoinedMoimRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static moim_today.global.constant.exception.JoinedMoimExceptionConstant.JOINED_MOIM_MEMBER_NOT_FOUND;
import static moim_today.global.constant.exception.JoinedMoimExceptionConstant.MEMBER_ALREADY_JOINED;

@Implement
public class JoinedMoimFinder {

    private final JoinedMoimRepository joinedMoimRepository;

    public JoinedMoimFinder(final JoinedMoimRepository joinedMoimRepository) {
        this.joinedMoimRepository = joinedMoimRepository;
    }

    @Transactional(readOnly = true)
    public List<JoinedMoimJpaEntity> findByMoimId(final long moimId) {
        return joinedMoimRepository.findJoinMembersByMoimId(moimId);
    }

    @Transactional(readOnly = true)
    public List<Long> findAllJoinedMemberId(final long moimId) {
        return joinedMoimRepository.findAllJoinedMemberId(moimId);
    }

    @Transactional(readOnly = true)
    public void validateMemberInMoim(final long memberId, final long moimId) {
        boolean isMemberInMoim = joinedMoimRepository.existsByMoimIdAndMemberId(moimId, memberId);
        if(!isMemberInMoim){
            throw new NotFoundException(JOINED_MOIM_MEMBER_NOT_FOUND.message());
        }
    }

    @Transactional(readOnly = true)
    public void validateMemberNotInMoim(final long moimId, final long memberId) {
        boolean isMemberInMoim = joinedMoimRepository.existsByMoimIdAndMemberId(moimId, memberId);
        if(isMemberInMoim){
            throw new BadRequestException(MEMBER_ALREADY_JOINED.message());
        }
    }

    public List<Long> findMoimIdsByMemberId(final long memberId) {
        return joinedMoimRepository.findMoimIdsByMemberId(memberId);
    }
}
