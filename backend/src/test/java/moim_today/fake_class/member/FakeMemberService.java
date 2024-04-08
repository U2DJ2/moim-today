package moim_today.fake_class.member;

import moim_today.application.member.MemberService;
import moim_today.domain.member.MemberSession;
import moim_today.dto.member.PasswordUpdateRequest;


public class FakeMemberService implements MemberService {

    @Override
    public void updatePassword(final MemberSession memberSession, final PasswordUpdateRequest passwordUpdateRequest) {

    }
}
