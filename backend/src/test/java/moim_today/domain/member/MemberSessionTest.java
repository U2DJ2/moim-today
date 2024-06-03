package moim_today.domain.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import moim_today.dto.auth.MemberSessionValidateResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import static moim_today.global.constant.SessionConstant.MEMBER_SESSION;
import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;


class MemberSessionTest {

    @DisplayName("세션 정보를 생성한다.")
    @Test
    void setSession() throws JsonProcessingException {
        // given
        MemberSession memberSession = new MemberSession(
                MEMBER_ID.longValue(),
                UNIV_ID.longValue(),
                DEPARTMENT_ID.longValue(),
                USERNAME.value(),
                PROFILE_IMAGE_URL.value()
        );

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequest request = new MockHttpServletRequest();
        String memberSessionJson = objectMapper.writeValueAsString(memberSession);

        // when
        memberSession.setSession(request, memberSessionJson, true);

        // then
        HttpSession session = request.getSession(false);
        assertThat(session).isNotNull();
    }

    @DisplayName("세션에 있는 정보가 일치하는지 확인한다.")
    @Test
    void setSessionCorrect() throws JsonProcessingException {
        // given
        MemberSession memberSession = new MemberSession(
                MEMBER_ID.longValue(),
                UNIV_ID.longValue(),
                DEPARTMENT_ID.longValue(),
                USERNAME.value(),
                PROFILE_IMAGE_URL.value()
        );

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequest request = new MockHttpServletRequest();
        String memberSessionJson = objectMapper.writeValueAsString(memberSession);

        // when
        memberSession.setSession(request, memberSessionJson, true);
        HttpSession session = request.getSession(false);
        assert session != null;
        String sessionAttribute = (String) session.getAttribute(MEMBER_SESSION.value());
        MemberSession findSession = objectMapper.readValue(sessionAttribute, MemberSession.class);

        // then
        assertThat(findSession.id()).isEqualTo(MEMBER_ID.longValue());
        assertThat(findSession.universityId()).isEqualTo(UNIV_ID.longValue());
        assertThat(findSession.departmentId()).isEqualTo(DEPARTMENT_ID.longValue());
        assertThat(findSession.username()).isEqualTo(USERNAME.value());
        assertThat(findSession.memberProfileImageUrl()).isEqualTo(PROFILE_IMAGE_URL.value());
    }

    @DisplayName("세션이 존재하면 true를 반환한다.")
    @Test
    void validateMemberSession() throws JsonProcessingException {
        // given
        MemberSession memberSession = new MemberSession(
                MEMBER_ID.longValue(),
                UNIV_ID.longValue(),
                DEPARTMENT_ID.longValue(),
                USERNAME.value(),
                PROFILE_IMAGE_URL.value()
        );

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequest request = new MockHttpServletRequest();
        String memberSessionJson = objectMapper.writeValueAsString(memberSession);
        memberSession.setSession(request, memberSessionJson, true);

        // when
        MemberSessionValidateResponse memberSessionValidateResponse = MemberSession.validateMemberSession(request);

        // then
        assertThat(memberSessionValidateResponse.isValidateMemberSession()).isTrue();
    }

    @DisplayName("세션이 존재하지 않으면 false 반환한다.")
    @Test
    void validateMemberSessionFail() {
        // given
        MockHttpServletRequest request = new MockHttpServletRequest();

        // when
        MemberSessionValidateResponse memberSessionValidateResponse = MemberSession.validateMemberSession(request);

        // then
        assertThat(memberSessionValidateResponse.isValidateMemberSession()).isFalse();
    }
}