package moim_today.implement.member;

import moim_today.dto.member.MemberProfileResponse;
import moim_today.dto.moim.moim.MoimMemberResponse;
import moim_today.global.annotation.Implement;
import moim_today.global.error.BadRequestException;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.member.MemberJpaEntity;
import moim_today.persistence.repository.member.MemberRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static moim_today.global.constant.exception.MemberExceptionConstant.EMAIL_ALREADY_USED_ERROR;
import static moim_today.global.constant.exception.MemberExceptionConstant.NO_MEMBERS_TO_FIND;

@Implement
public class MemberFinder {

    private final MemberRepository memberRepository;

    public MemberFinder(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public void validateEmailExists(final String email) {
        memberRepository.validateEmailExists(email);
    }

    @Transactional(readOnly = true)
    public void validateAlreadyExists(final String email) {
        memberRepository.validateAlreadyExists(email);
    }

    @Transactional(readOnly = true)
    public MemberProfileResponse getMemberProfile(final long memberId) {
        return memberRepository.getMemberProfile(memberId);
    }

    @Transactional(readOnly = true)
    public void validateEmailNotExists(final String email){
        Optional<MemberJpaEntity> findMember = memberRepository.findByEmail(email);
        if(findMember.isPresent()){
            throw new BadRequestException(EMAIL_ALREADY_USED_ERROR.message());
        }
    }

    @Transactional(readOnly = true)
    public MemberJpaEntity getMemberById(final long memberId) {
        return memberRepository.getById(memberId);
    }

    @Transactional(readOnly = true)
    public List<MemberJpaEntity> getMemberByIdIn(final List<Long> memberIds){
        if(!memberIds.isEmpty()){
            return memberRepository.findByIdIn(memberIds);
        }
        throw new NotFoundException(NO_MEMBERS_TO_FIND.message());
    }

    @Transactional(readOnly = true)
    public List<MoimMemberResponse> findMembersWithJoinedInfo(final List<Long> memberIds, final long hostId) {
        return memberRepository.findMembersWithJoinInfo(memberIds, hostId);
    }
}
