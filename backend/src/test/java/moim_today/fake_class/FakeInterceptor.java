package booki_today.fake_class;

import booki_today.domain.member.MemberSession;
import booki_today.fake_DB.FakeMemberSession;
import booki_today.global.error.InternalServerException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import static booki_today.global.constant.MemberSessionConstant.MEMBER_SESSION;
import static booki_today.global.constant.SessionExceptionConstant.MEMBER_SESSION_JSON_PROCESSING_ERROR;

public class FakeInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper;

    public FakeInterceptor(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, HttpServletResponse response, final Object handler) throws JsonProcessingException {
        MemberSession fakeMemberSession = FakeMemberSession.createMemberSession();

        String memberSessionJson = objectMapper.writeValueAsString(fakeMemberSession);

        try {
            MemberSession memberSession = objectMapper.readValue(memberSessionJson, MemberSession.class);
            request.setAttribute(MEMBER_SESSION.value(), memberSession);
        } catch (JsonProcessingException e) {
            throw new InternalServerException(MEMBER_SESSION_JSON_PROCESSING_ERROR.message());
        }

        return true;
    }
}
