package moim_today.application.admin.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import moim_today.dto.admin.auth.AdminLoginRequest;
import moim_today.global.error.BadRequestException;
import moim_today.persistence.entity.university.UniversityJpaEntity;
import moim_today.persistence.repository.university.UniversityRepository;
import moim_today.util.DatabaseCleaner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;

import static moim_today.global.constant.NumberConstant.ONE_DAYS_IN_SECONDS;
import static moim_today.global.constant.SessionConstant.ADMIN_SESSION;
import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class AdminAuthServiceTest {

    @Autowired
    private AdminAuthService adminAuthService;

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUpDatabase() {
        databaseCleaner.cleanUp();
    }

    @DisplayName("올바른 비밀번호가 입력되면 관리자 로그인에 성공하고 세션 값이 하루로 설정된다.")
    @Test
    void loginSuccess() {
        //given
        String adminPassword = ADMIN_PASSWORD.value();

        UniversityJpaEntity universityJpaEntity = UniversityJpaEntity.builder()
                .universityEmail(AJOU_EMAIL.value())
                .universityName(AJOU_UNIVERSITY_NAME.value())
                .adminPassword(adminPassword)
                .build();

        universityRepository.save(universityJpaEntity);
        long universityId = universityJpaEntity.getId();

        AdminLoginRequest adminLoginRequest = new AdminLoginRequest(universityId, adminPassword);
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

        //when
        adminAuthService.login(adminLoginRequest, mockHttpServletRequest);

        //then
        HttpSession session = mockHttpServletRequest.getSession(false);
        assert session != null;
        assertThat(session.getAttribute(ADMIN_SESSION.value())).isNotNull();
        assertThat(session.getMaxInactiveInterval()).isEqualTo(ONE_DAYS_IN_SECONDS.value());
    }

    @DisplayName("잘못된 정보가 입력되면 400 예외를 발생시킨다.")
    @Test
    void loginFail() {
        //given
        String password = ADMIN_PASSWORD.value();
        String wrongPassword = WRONG_ADMIN_PASSWORD.value();

        UniversityJpaEntity universityJpaEntity = UniversityJpaEntity.builder()
                .universityEmail(AJOU_EMAIL.value())
                .universityName(AJOU_UNIVERSITY_NAME.value())
                .adminPassword(password)
                .build();

        universityRepository.save(universityJpaEntity);
        long universityId = universityJpaEntity.getId();

        AdminLoginRequest adminLoginRequest = new AdminLoginRequest(universityId, wrongPassword);
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

        //expected
        assertThatThrownBy(() -> adminAuthService.login(adminLoginRequest, mockHttpServletRequest))
                .isInstanceOf(BadRequestException.class);

        assertThat(mockHttpServletRequest.getSession(false)).isNull();
    }

    @DisplayName("로그아웃을 요청하면 session을 무효로 만든다.")
    @Test
    void logoutTest() {
        //given
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        HttpSession session = mockRequest.getSession(true);
        assert session != null;
        session.setAttribute(ADMIN_SESSION.value(), ADMIN_SESSION_VALUE.value());

        //when
        adminAuthService.logout(mockRequest);

        //then
        assertThat(mockRequest.getSession(false)).isNull();
    }
}