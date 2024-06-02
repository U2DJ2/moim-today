package moim_today.application.admin.member;

import moim_today.dto.member.MemberResponse;
import moim_today.global.error.ForbiddenException;
import moim_today.persistence.entity.member.MemberJpaEntity;
import moim_today.persistence.repository.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static moim_today.global.constant.exception.AdminExceptionConstant.ADMIN_FORBIDDEN_ERROR;

@Service
public class AdminMemberService {

    private final MemberRepository memberRepository;

    public AdminMemberService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> findAllMembers(final long universityId, final long departmentId) {
        return memberRepository.findByUniversityIdAndDepartmentId(universityId, departmentId);
    }

    @Transactional
    public void deleteMember(final long universityId, final long memberId) {
        MemberJpaEntity member = memberRepository.getById(memberId);

        if (member.getUniversityId() != universityId) {
            throw new ForbiddenException(ADMIN_FORBIDDEN_ERROR.message());
        }

        member.deleteMember();
    }
}
