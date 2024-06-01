package moim_today.global.argumentresolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import moim_today.domain.university.AdminSession;
import moim_today.global.annotation.Login;
import moim_today.global.error.InternalServerException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static moim_today.global.constant.SessionConstant.ADMIN_SESSION;
import static moim_today.global.constant.exception.SessionExceptionConstant.ADMIN_SESSION_GET_ATTRIBUTE_ERROR;
import static moim_today.global.constant.exception.SessionExceptionConstant.ADMIN_SESSION_JSON_PROCESSING_ERROR;

public class AdminLoginArgumentResolver implements HandlerMethodArgumentResolver {

    private final ObjectMapper objectMapper;

    public AdminLoginArgumentResolver(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        boolean isAssignable = AdminSession.class.isAssignableFrom(parameter.getParameterType());
        return hasLoginAnnotation && isAssignable;
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
        String adminSessionJson = (String)request.getAttribute(ADMIN_SESSION.value());
        if (adminSessionJson == null) {
            throw new InternalServerException(ADMIN_SESSION_GET_ATTRIBUTE_ERROR.message());
        }

        try {
            return objectMapper.readValue(adminSessionJson, AdminSession.class);
        } catch (JsonProcessingException e) {
            throw new InternalServerException(ADMIN_SESSION_JSON_PROCESSING_ERROR.message());
        }
    }
}
