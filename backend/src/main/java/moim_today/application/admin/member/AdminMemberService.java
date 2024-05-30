package moim_today.application.admin.member;

import moim_today.dto.member.MemberResponse;
import moim_today.global.error.ForbiddenException;
import moim_today.persistence.entity.member.MemberJpaEntity;
import moim_today.persistence.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static moim_today.global.constant.exception.AdminExceptionConstant.ADMIN_FORBIDDEN_ERROR;

@Service
public class AdminMemberService {

    private final MemberRepository memberRepository;

    @Value("${admin.email}")
    private String adminEmail;

    public AdminMemberService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<MemberResponse> findAllMembers(final long memberId, final long universityId, final long departmentId) {
        MemberJpaEntity member = memberRepository.getById(memberId);
        if(isNotAdmin(adminEmail, member.getEmail())) {
            throw new ForbiddenException(ADMIN_FORBIDDEN_ERROR.message());
        }

        return memberRepository.findByUniversityIdAndDepartmentId(universityId, departmentId);
    }

    private boolean isNotAdmin(final String adminEmail, final String memberEmail) {
        return !(memberEmail.equals(adminEmail));
    }
}
