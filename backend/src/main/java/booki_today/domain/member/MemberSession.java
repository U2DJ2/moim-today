package booki_today.domain.member;

import booki_today.global.error.InternalServerException;
import booki_today.persistence.entity.member.MemberJpaEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static booki_today.global.constant.SessionExceptionConstant.MEMBER_SESSION_JSON_PROCESSING_ERROR;

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
}
