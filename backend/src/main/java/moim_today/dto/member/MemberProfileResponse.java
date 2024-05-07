package moim_today.dto.member;

import com.fasterxml.jackson.annotation.JsonFormat;
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

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate birthDate,
        Gender gender,
        String memberProfileImageUrl
) {

    @QueryProjection
    public MemberProfileResponse {
    }
}
