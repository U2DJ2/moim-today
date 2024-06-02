package moim_today.implement.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpSession;
import moim_today.domain.member.MemberSession;
import moim_today.domain.member.enums.Gender;
import moim_today.dto.auth.MemberLoginRequest;
import moim_today.dto.auth.MemberSignUpRequest;
import moim_today.fake_DB.FakeMemberSession;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.member.MemberJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

import java.time.LocalDate;

import static moim_today.global.constant.SessionConstant.MEMBER_SESSION;
import static moim_today.global.constant.NumberConstant.ONE_DAYS_IN_SECONDS;
import static moim_today.global.constant.NumberConstant.THIRTY_DAYS_IN_SECONDS;
import static moim_today.util.TestConstant.*;
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
        MemberLoginRequest memberLoginRequest = new MemberLoginRequest(EMAIL.value(), PASSWORD.value(), true);
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

        //when
        authManager.login(memberLoginRequest, mockHttpServletRequest);

        //then
        HttpSession session = mockHttpServletRequest.getSession(false);
        assert session != null;
        assertThat(session.getAttribute(MEMBER_SESSION.value())).isNotNull();
    }

    @DisplayName("로그인 유지를 하지 않으면 세션의 값이 하루로 설정된다.")
    @Test
    void noKeepLoginTest() {
        //given
        MemberJpaEntity entity = MemberJpaEntity.builder().email(EMAIL.value())
                .password(passwordEncoder.encode(PASSWORD.value())).build();

        memberRepository.save(entity);
        MemberLoginRequest memberLoginRequest = new MemberLoginRequest(EMAIL.value(), PASSWORD.value(), false);
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

        //when
        authManager.login(memberLoginRequest, mockHttpServletRequest);

        //then
        HttpSession session = mockHttpServletRequest.getSession(false);
        assert session != null;
        assertThat(session.getMaxInactiveInterval()).isEqualTo(ONE_DAYS_IN_SECONDS.value());
    }

    @DisplayName("로그인 유지를 선택하면 세션의 값이 한달로 설정된다.")
    @Test
    void keepLoginTest() {
        //given
        MemberJpaEntity entity = MemberJpaEntity.builder().email(EMAIL.value())
                .password(passwordEncoder.encode(PASSWORD.value())).build();

        memberRepository.save(entity);
        MemberLoginRequest memberLoginRequest = new MemberLoginRequest(EMAIL.value(), PASSWORD.value(), true);
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

        //when
        authManager.login(memberLoginRequest, mockHttpServletRequest);

        //then
        HttpSession session = mockHttpServletRequest.getSession(false);
        assert session != null;
        assertThat(session.getMaxInactiveInterval()).isEqualTo(THIRTY_DAYS_IN_SECONDS.value());
    }

    @DisplayName("잘못된 정보가 입력되면 404 예외를 발생시킨다.")
    @Test
    void loginFail() {
        //given
        MemberJpaEntity entity = MemberJpaEntity.builder().email(EMAIL.value())
                .password(passwordEncoder.encode(PASSWORD.value())).build();

        memberRepository.save(entity);
        MemberLoginRequest memberLoginRequest = new MemberLoginRequest(WRONG_EMAIL.value(), WRONG_PASSWORD.value(), true);
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

        //when && then
        assertThatThrownBy(() -> authManager.login(memberLoginRequest, mockHttpServletRequest))
                .isInstanceOf(NotFoundException.class);

        assertThat(mockHttpServletRequest.getSession(false)).isNull();
    }

    @DisplayName("로그아웃을 요청하면 session을 무효로 만든다.")
    @Test
    void logoutTest() {
        //given
        MemberSession memberSession = FakeMemberSession.createMemberSession();
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        HttpSession session = mockRequest.getSession(true);
        assert session != null;
        session.setAttribute(MEMBER_SESSION.value(), memberSession);

        //when
        authManager.logout(mockRequest);

        //then
        assertThat(mockRequest.getSession(false)).isNull();
    }

    @DisplayName("정상적으로 회원가입을 완료하면 멤버 데이터를 넣고 세션에 등록한다")
    @Test
    void signUp() throws JsonProcessingException {
        // given
        LocalDate birthDate = LocalDate.now();
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

        MemberSignUpRequest memberSignUpRequest = MemberSignUpRequest.builder()
                .email(EMAIL.value())
                .password(PASSWORD.value())
                .universityId(Long.parseLong(UNIV_ID.value()))
                .departmentId(Long.parseLong(DEPARTMENT_ID.value()))
                .studentId(STUDENT_ID.value())
                .birthDate(birthDate)
                .gender(Gender.MALE)
                .username(USERNAME.value())
                .build();

        // when
        authManager.signUp(memberSignUpRequest, mockHttpServletRequest);

        // then
        HttpSession session = mockHttpServletRequest.getSession(false);
        assertThat(session).isNotNull();
        String memberSessionObject = (String) mockHttpServletRequest.getSession().getAttribute(MEMBER_SESSION.value());
        assertThat(memberSessionObject).isNotBlank();
        MemberSession memberSession = objectMapper.readValue(memberSessionObject, MemberSession.class);
        assertThat(memberSession.username()).isEqualTo(USERNAME.value());
        assertThat(emailSubscribeRepository.count()).isEqualTo(1);
    }
}