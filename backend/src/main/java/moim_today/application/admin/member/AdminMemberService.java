package moim_today.application.admin.member;

import moim_today.dto.member.MemberResponse;
import moim_today.persistence.repository.member.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminMemberService {

    private final MemberRepository memberRepository;

    public AdminMemberService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<MemberResponse> findAllMembers(final long universityId, final long departmentId) {
        return memberRepository.findByUniversityIdAndDepartmentId(universityId, departmentId);
    }
}
