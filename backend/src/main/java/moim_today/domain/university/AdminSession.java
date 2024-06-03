package moim_today.domain.university;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import moim_today.dto.admin.auth.AdminLoginRequest;
import moim_today.global.error.InternalServerException;

import static moim_today.global.constant.NumberConstant.ONE_DAYS_IN_SECONDS;
import static moim_today.global.constant.SessionConstant.ADMIN_SESSION;
import static moim_today.global.constant.exception.SessionExceptionConstant.ADMIN_SESSION_JSON_PROCESSING_ERROR;

public record AdminSession(
        long universityId
) {

    public static AdminSession from(final AdminLoginRequest loginRequest) {
        return new AdminSession(loginRequest.universityId());
    }

    public String toJson(final ObjectMapper objectMapper) {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new InternalServerException(ADMIN_SESSION_JSON_PROCESSING_ERROR.message());
        }
    }

    public void setSession(final HttpServletRequest request, final String adminSessionJson) {
        HttpSession session = request.getSession(true);
        session.setAttribute(ADMIN_SESSION.value(), adminSessionJson);
        session.setMaxInactiveInterval(ONE_DAYS_IN_SECONDS.value());
    }
}
