package moim_today.application.admin.member;

import moim_today.dto.member.MemberResponse;

import java.util.List;

public interface AdminMemberService {

    List<MemberResponse> findAllMembers(final long memberId, final long universityId, final long departmentId);
}
