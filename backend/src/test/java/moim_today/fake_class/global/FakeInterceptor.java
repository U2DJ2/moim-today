package moim_today.fake_class.global;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import moim_today.domain.member.MemberSession;
import moim_today.fake_DB.FakeMemberSession;
import moim_today.global.error.InternalServerException;
import org.springframework.web.servlet.HandlerInterceptor;

import static moim_today.global.constant.SessionConstant.MEMBER_SESSION;
import static moim_today.global.constant.exception.SessionExceptionConstant.MEMBER_SESSION_JSON_PROCESSING_ERROR;


public class FakeInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper;

    public FakeInterceptor(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler){
        MemberSession fakeMemberSession = FakeMemberSession.createMemberSession();

        try {
            String memberSessionJson = objectMapper.writeValueAsString(fakeMemberSession);
            request.setAttribute(MEMBER_SESSION.value(), memberSessionJson);
        } catch (JsonProcessingException e) {
            throw new InternalServerException(MEMBER_SESSION_JSON_PROCESSING_ERROR.message());
        }
        return true;
    }
}
