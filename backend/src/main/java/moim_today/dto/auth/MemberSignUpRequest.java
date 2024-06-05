package moim_today.dto.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import moim_today.domain.member.enums.Gender;
import moim_today.persistence.entity.member.MemberJpaEntity;

import java.time.LocalDate;

import static moim_today.global.constant.MemberConstant.DEFAULT_PROFILE_URL;
import static moim_today.global.constant.exception.ValidationExceptionConstant.*;

@Builder
public record MemberSignUpRequest(
        @Min(value = 0, message = UNIVERSITY_ID_MIN_ERROR) long universityId,
        @Min(value = 0, message = DEPARTMENT_ID_MIN_ERROR) long departmentId,
        @Email(message = EMAIL_INVALID_ERROR) @NotBlank(message = EMAIL_BLANK_ERROR) String email,
        @NotBlank(message = MEMBER_PASSWORD_BLANK_ERROR) String password,
        @NotBlank(message = USERNAME_BLANK_ERROR) String username,
        @NotBlank(message = STUDENT_ID_BLANK_ERROR) String studentId,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-M-d", timezone = "Asia/Seoul")
        @NotNull(message = BIRTH_DATE_NULL_ERROR) LocalDate birthDate,
        @NotNull(message = GENDER_NULL_ERROR) Gender gender
) {

    public MemberJpaEntity toEntity(final String encodedPassword) {
        return MemberJpaEntity.builder()
            .universityId(universityId)
            .departmentId(departmentId)
            .email(email)
            .password(encodedPassword)
            .username(username)
            .studentId(studentId)
            .birthDate(birthDate)
            .gender(gender)
            .memberProfileImageUrl(DEFAULT_PROFILE_URL.value())
            .build();
    }
}
