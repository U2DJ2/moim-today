package moim_today.implement.admin.member;

import moim_today.dto.member.MemberResponse;
import moim_today.global.annotation.Implement;
import moim_today.global.error.ForbiddenException;
import moim_today.persistence.entity.member.MemberJpaEntity;
import moim_today.persistence.repository.member.MemberRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static moim_today.global.constant.exception.AdminExceptionConstant.ADMIN_FORBIDDEN_ERROR;

@Implement
public class MemberAdmin {

    private final MemberRepository memberRepository;

    public MemberAdmin(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public void validateAdmin(final String adminEmail, final long memberId) {
        MemberJpaEntity member = memberRepository.getById(memberId);
        if(isNotAdmin(adminEmail, member.getEmail())) {
            throw new ForbiddenException(ADMIN_FORBIDDEN_ERROR.message());
        }
    }

    public List<MemberResponse> findAllMembers(final long universityId, final long departmentId) {
        return memberRepository.findByUniversityIdAndDepartmentId(universityId, departmentId);
    }

    public void validateAdmin(final String adminEmail, final String memberEmail) {
        if(isNotAdmin(adminEmail, memberEmail)) {
            throw new ForbiddenException(ADMIN_FORBIDDEN_ERROR.message());
        }
    }

    private boolean isNotAdmin(final String adminEmail, final String memberEmail) {
        return !(memberEmail.equals(adminEmail));
    }
}
