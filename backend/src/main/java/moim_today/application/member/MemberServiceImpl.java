package moim_today.application.member;

import moim_today.domain.member.MemberSession;
import moim_today.dto.member.PasswordRecoverRequest;
import moim_today.dto.member.PasswordUpdateRequest;
import moim_today.implement.member.MemberUpdater;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberUpdater memberUpdater;

    public MemberServiceImpl(final MemberUpdater memberUpdater) {
        this.memberUpdater = memberUpdater;
    }

    @Override
    public void updatePassword(final MemberSession memberSession, final PasswordUpdateRequest passwordUpdateRequest) {
        memberUpdater.updatePassword(memberSession.id(), passwordUpdateRequest.newPassword());
    }

    @Override
    public void recoverPassword(final PasswordRecoverRequest passwordRecoverRequest) {
        memberUpdater.recoverPassword(
                passwordRecoverRequest.passwordToken(),
                passwordRecoverRequest.newPassword(),
                LocalDateTime.now()
        );
    }
}
