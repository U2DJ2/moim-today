package moim_today.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import moim_today.global.error.UnauthorizedException;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import static moim_today.global.constant.SessionConstant.MEMBER_SESSION;
import static moim_today.global.constant.exception.SessionExceptionConstant.MEMBER_SESSION_UNAUTHORIZED;

public class MemberLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler){
        if(StringUtils.equals(request.getMethod(), "OPTIONS")){
            return true;
        }

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(MEMBER_SESSION.value()) == null) {
            throw new UnauthorizedException(MEMBER_SESSION_UNAUTHORIZED.message());
        }

        String memberSessionJson = (String)session.getAttribute(MEMBER_SESSION.value());
        request.setAttribute(MEMBER_SESSION.value(), memberSessionJson);

        return true;
    }
}
