package moim_today.application.admin.member;

import moim_today.dto.member.MemberResponse;
import moim_today.implement.admin.member.MemberAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminMemberServiceImpl implements AdminMemberService {

    private final MemberAdmin memberAdmin;

    @Value("${admin.email}")
    private String adminEmail;

    public AdminMemberServiceImpl(final MemberAdmin memberAdmin) {
        this.memberAdmin = memberAdmin;
    }

    @Override
    public List<MemberResponse> findAllMembers(final long memberId, final long universityId, final long departmentId) {
        memberAdmin.validateAdmin(adminEmail, memberId);
        return memberAdmin.findAllMembers(universityId, departmentId);
    }
}
