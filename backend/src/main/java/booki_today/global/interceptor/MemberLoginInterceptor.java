package booki_today.global.interceptor;

import booki_today.domain.member.MemberSession;
import booki_today.global.error.InternalServerException;
import booki_today.global.error.UnauthorizedException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import static booki_today.global.constant.MemberSessionConstant.MEMBER_SESSION;
import static booki_today.global.constant.SessionExceptionConstant.MEMBER_SESSION_JSON_PROCESSING_ERROR;
import static booki_today.global.constant.SessionExceptionConstant.MEMBER_SESSION_UNAUTHORIZED;

public class MemberLoginInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper;

    public MemberLoginInterceptor(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(MEMBER_SESSION.value()) == null) {
            throw new UnauthorizedException(MEMBER_SESSION_UNAUTHORIZED.message());
        }

        String memberSessionJson = (String)session.getAttribute(MEMBER_SESSION.value());

        try {
            MemberSession memberSession = objectMapper.readValue(memberSessionJson, MemberSession.class);
            request.setAttribute(MEMBER_SESSION.value(), memberSession);
        } catch (JsonProcessingException e) {
            throw new InternalServerException(MEMBER_SESSION_JSON_PROCESSING_ERROR.message());
        }

        return true;
    }
}
