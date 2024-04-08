package moim_today.domain.member;

import lombok.Builder;
import moim_today.global.error.InternalServerException;
import moim_today.persistence.entity.member.MemberJpaEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static moim_today.global.constant.SessionExceptionConstant.MEMBER_SESSION_JSON_PROCESSING_ERROR;

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
}
