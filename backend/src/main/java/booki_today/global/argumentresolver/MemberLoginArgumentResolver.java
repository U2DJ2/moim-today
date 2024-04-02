package booki_today.global.argumentresolver;

import booki_today.domain.member.MemberSession;
import booki_today.global.annotation.Login;
import booki_today.global.error.InternalServerException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static booki_today.global.constant.MemberSessionConstant.MEMBER_SESSION;
import static booki_today.global.constant.SessionExceptionConstant.MEMBER_SESSION_GET_ATTRIBUTE_ERROR;

public class MemberLoginArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        boolean isAssignable = MemberSession.class.isAssignableFrom(parameter.getParameterType());
        return hasLoginAnnotation && isAssignable;
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
        Object memberSession = request.getAttribute(MEMBER_SESSION.value());
        if (memberSession == null) {
            throw new InternalServerException(MEMBER_SESSION_GET_ATTRIBUTE_ERROR.message());
        }
        return memberSession;
    }
}
