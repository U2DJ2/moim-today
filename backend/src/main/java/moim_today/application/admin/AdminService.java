package moim_today.application.admin;

import jakarta.servlet.http.HttpServletRequest;
import moim_today.dto.auth.MemberLoginRequest;
import moim_today.dto.member.MemberResponse;

import java.util.List;

public interface AdminService {

    List<MemberResponse> findAllMembers(final long memberId, final long universityId, final long departmentId);

    void login(final MemberLoginRequest loginRequest, final HttpServletRequest request);

    boolean validateAdmin(final long memberId);
}
