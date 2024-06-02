package moim_today.application.admin.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import moim_today.domain.university.AdminSession;
import moim_today.dto.admin.auth.AdminLoginRequest;
import moim_today.global.error.BadRequestException;
import moim_today.persistence.repository.university.UniversityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static moim_today.global.constant.exception.AdminExceptionConstant.ADMIN_PASSWORD_ERROR;


@Service
public class AdminAuthService {

    private final UniversityRepository universityRepository;
    private final ObjectMapper objectMapper;

    public AdminAuthService(
            final UniversityRepository universityRepository,
            final ObjectMapper objectMapper) {
        this.universityRepository = universityRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional(readOnly = true)
    public void login(final AdminLoginRequest loginRequest, final HttpServletRequest request) {
        String actualAdminPassword = universityRepository.getAdminPasswordById(loginRequest.universityId());
        validateAdminPassword(loginRequest.adminPassword(), actualAdminPassword);

        AdminSession adminSession = AdminSession.from(loginRequest);
        String adminSessionJson = adminSession.toJson(objectMapper);
        adminSession.setSession(request, adminSessionJson);
    }

    public void logout(final HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    private void validateAdminPassword(final String requestAdminPassword, final String actualAdminPassword) {
        if (!actualAdminPassword.equals(requestAdminPassword)) {
            throw new BadRequestException(ADMIN_PASSWORD_ERROR.message());
        }
    }
}
