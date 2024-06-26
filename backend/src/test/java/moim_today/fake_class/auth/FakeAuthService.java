package moim_today.fake_class.auth;

import moim_today.application.auth.AuthService;
import moim_today.dto.auth.MemberLoginRequest;
import moim_today.dto.auth.MemberSignUpRequest;
import moim_today.dto.auth.MemberSessionValidateResponse;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.member.MemberJpaEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

import static moim_today.global.constant.exception.MemberExceptionConstant.EMAIL_PASSWORD_ERROR;
import static moim_today.util.TestConstant.EMAIL;
import static moim_today.util.TestConstant.PASSWORD;

public class FakeAuthService implements AuthService {

    private static Map<Long, MemberJpaEntity> fakeDB = new HashMap<>();

    @Override
    public void login(final MemberLoginRequest memberLoginRequest, final HttpServletRequest request) {
        MemberJpaEntity entity = MemberJpaEntity.builder()
                .email(EMAIL.value())
                .password(PASSWORD.value())
                .build();

        MemberJpaEntity memberJpaEntity = fakeDB.getOrDefault(1L, entity);

        if (memberLoginRequest.email().equals(memberJpaEntity.getEmail()) &&
                memberLoginRequest.password().equals(memberJpaEntity.getPassword())) {
            return;
        }

        throw new NotFoundException(EMAIL_PASSWORD_ERROR.message());
    }

    @Override
    public void signUp(final MemberSignUpRequest memberSignUpRequest, final HttpServletRequest request) {

    }

    @Override
    public void logout(final HttpServletRequest request) {

    }

    @Override
    public MemberSessionValidateResponse validateMemberSession(final HttpServletRequest request) {
        return new MemberSessionValidateResponse(true);
    }
}
