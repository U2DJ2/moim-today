package moim_today.global.argumentresolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import moim_today.domain.member.MemberSession;
import moim_today.global.annotation.Login;
import moim_today.global.error.InternalServerException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static moim_today.global.constant.SessionConstant.MEMBER_SESSION;
import static moim_today.global.constant.exception.SessionExceptionConstant.MEMBER_SESSION_GET_ATTRIBUTE_ERROR;
import static moim_today.global.constant.exception.SessionExceptionConstant.MEMBER_SESSION_JSON_PROCESSING_ERROR;

public class MemberLoginArgumentResolver implements HandlerMethodArgumentResolver {

    private final ObjectMapper objectMapper;

    public MemberLoginArgumentResolver(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        boolean isAssignable = MemberSession.class.isAssignableFrom(parameter.getParameterType());
        return hasLoginAnnotation && isAssignable;
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
        String memberSessionJson = (String)request.getAttribute(MEMBER_SESSION.value());
        if (memberSessionJson == null) {
            throw new InternalServerException(MEMBER_SESSION_GET_ATTRIBUTE_ERROR.message());
        }

        try {
            return objectMapper.readValue(memberSessionJson, MemberSession.class);
        } catch (JsonProcessingException e) {
            throw new InternalServerException(MEMBER_SESSION_JSON_PROCESSING_ERROR.message());
        }
    }
}
