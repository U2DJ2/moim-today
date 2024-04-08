package moim_today.application.member;

import moim_today.domain.member.MemberSession;
import moim_today.dto.member.PasswordUpdateRequest;

public interface MemberService {

    void updatePassword(final MemberSession memberSession, final PasswordUpdateRequest passwordUpdateRequest);
}
