package moim_today.implement.member;

import moim_today.dto.member.MemberProfileResponse;
import moim_today.global.annotation.Implement;
import moim_today.global.error.BadRequestException;
import moim_today.persistence.entity.member.MemberJpaEntity;
import moim_today.persistence.repository.member.MemberRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static moim_today.global.constant.exception.MemberExceptionConstant.EMAIL_ALREADY_USED_ERROR;

@Implement
public class MemberFinder {

    private final MemberRepository memberRepository;

    public MemberFinder(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void validateEmailExists(final String email) {
        memberRepository.validateEmailExists(email);
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
}
