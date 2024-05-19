package moim_today.domain.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Builder;
import moim_today.dto.auth.MemberSessionValidateResponse;
import moim_today.global.error.InternalServerException;
import moim_today.persistence.entity.member.MemberJpaEntity;

import static moim_today.global.constant.MemberSessionConstant.MEMBER_SESSION;
import static moim_today.global.constant.NumberConstant.ONE_DAYS_IN_SECONDS;
import static moim_today.global.constant.NumberConstant.THIRTY_DAYS_IN_SECONDS;
import static moim_today.global.constant.exception.SessionExceptionConstant.MEMBER_SESSION_JSON_PROCESSING_ERROR;

@Builder
public record MemberSession(
        long id,
        long universityId,
        long departmentId,
        String username,
        String memberProfileImageUrl
) {

    public static MemberSession from(final MemberJpaEntity entity) {
        return new MemberSession(
                entity.getId(),
                entity.getUniversityId(),
                entity.getDepartmentId(),
                entity.getUsername(),
                entity.getMemberProfileImageUrl()
        );
    }

    public String toJson(final ObjectMapper objectMapper) {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new InternalServerException(MEMBER_SESSION_JSON_PROCESSING_ERROR.message());
        }
    }

    public void setSession(final HttpServletRequest request, final String memberSessionJson, final boolean isKeepLogin) {
        HttpSession session = request.getSession(true);
        session.setAttribute(MEMBER_SESSION.value(), memberSessionJson);

        if (isKeepLogin) {
            session.setMaxInactiveInterval(THIRTY_DAYS_IN_SECONDS.value());
        } else {
            session.setMaxInactiveInterval(ONE_DAYS_IN_SECONDS.value());
        }
    }

    public static MemberSessionValidateResponse validateMemberSession(final HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(MEMBER_SESSION.value()) == null) {
            return new MemberSessionValidateResponse(false);
        }
        return new MemberSessionValidateResponse(true);
    }
}
