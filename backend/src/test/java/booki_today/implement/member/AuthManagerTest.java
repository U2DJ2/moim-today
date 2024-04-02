package booki_today.implement.member;

import booki_today.dto.auth.MemberLoginRequest;
import booki_today.global.error.NotFoundException;
import booki_today.persistence.entity.member.MemberJpaEntity;
import booki_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

import static booki_today.global.constant.MemberSessionConstant.MEMBER_SESSION;
import static booki_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AuthManagerTest extends ImplementTest {

    @Autowired
    private AuthManager authManager;

    @DisplayName("올바른 정보가 입력되면 로그인에 성공한다.")
    @Test
    void loginSuccess() {
        //given
        MemberJpaEntity entity = MemberJpaEntity.builder().email(EMAIL.value())
                .password(passwordEncoder.encode(PASSWORD.value())).build();

        memberRepository.save(entity);
        MemberLoginRequest memberLoginRequest = new MemberLoginRequest(EMAIL.value(), PASSWORD.value());
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

        //when
        authManager.login(memberLoginRequest, mockHttpServletRequest);

        //then
        assertThat(mockHttpServletRequest.getSession(false)).isNotNull();
        assertThat(mockHttpServletRequest.getSession(false).getAttribute(MEMBER_SESSION.value())).isNotNull();
    }

    @DisplayName("잘못된 정보가 입력되면 404 예외를 발생시킨다.")
    @Test
    void loginFail() {
        //given
        MemberJpaEntity entity = MemberJpaEntity.builder().email(EMAIL.value())
                .password(passwordEncoder.encode(PASSWORD.value())).build();

        memberRepository.save(entity);
        MemberLoginRequest memberLoginRequest = new MemberLoginRequest(WRONG_EMAIL.value(), WRONG_PASSWORD.value());
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

        //when && then
        assertThatThrownBy(() -> authManager.login(memberLoginRequest, mockHttpServletRequest))
                .isInstanceOf(NotFoundException.class);

        assertThat(mockHttpServletRequest.getSession(false)).isNull();
    }
}