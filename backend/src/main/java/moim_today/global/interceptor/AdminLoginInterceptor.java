package moim_today.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import moim_today.global.error.UnauthorizedException;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import static moim_today.global.constant.SessionConstant.ADMIN_SESSION;
import static moim_today.global.constant.exception.SessionExceptionConstant.ADMIN_SESSION_UNAUTHORIZED;

public class AdminLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler){
        if(StringUtils.equals(request.getMethod(), "OPTIONS")){
            return true;
        }

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(ADMIN_SESSION.value()) == null) {
            throw new UnauthorizedException(ADMIN_SESSION_UNAUTHORIZED.message());
        }

        String adminSessionJson = (String)session.getAttribute(ADMIN_SESSION.value());
        request.setAttribute(ADMIN_SESSION.value(), adminSessionJson);

        return true;
    }
}
