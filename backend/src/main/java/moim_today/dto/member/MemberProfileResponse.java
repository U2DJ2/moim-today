package moim_today.dto.member;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import moim_today.domain.member.enums.Gender;

import java.time.LocalDate;

@Builder
public record MemberProfileResponse(
        String universityName,
        String departmentName,
        String email,
        String username,
        String studentId,
        LocalDate birthDate,
        Gender gender,
        String memberProfileImageUrl
) {

    @QueryProjection
    public MemberProfileResponse {
    }
}
