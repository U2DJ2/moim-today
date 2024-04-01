package booki_today.fake_class;

import booki_today.application.auth.AuthService;
import booki_today.dto.auth.MemberLoginRequest;
import booki_today.global.error.NotFoundException;
import booki_today.persistence.entity.member.MemberJpaEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

import static booki_today.global.constant.MemberExceptionConstant.EMAIL_PASSWORD_ERROR;

public class FakeAuthService implements AuthService {

    private static Map<Long, MemberJpaEntity> fakeDB = new HashMap<>();

    @Override
    public void login(final MemberLoginRequest memberLoginRequest, final HttpServletRequest request) {
        MemberJpaEntity entity = MemberJpaEntity.builder()
                .email("email")
                .password("password")
                .build();

        MemberJpaEntity memberJpaEntity = fakeDB.getOrDefault(1L, entity);

        if (memberLoginRequest.email().equals(memberJpaEntity.getEmail()) &&
                memberLoginRequest.password().equals(memberJpaEntity.getPassword())) {
            return;
        }

        throw new NotFoundException(EMAIL_PASSWORD_ERROR.message());

    }
}
