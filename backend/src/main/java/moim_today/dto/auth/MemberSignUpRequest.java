package moim_today.dto.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import moim_today.domain.member.enums.Gender;
import moim_today.persistence.entity.member.MemberJpaEntity;

import java.time.LocalDate;

import static moim_today.global.constant.MemberConstant.DEFAULT_PROFILE_URL;

@Builder
public record MemberSignUpRequest(
        long universityId,
        long departmentId,
        @Email(message = INVALID_EMAIL_FORMAT) @NotBlank(message = NO_EMAIL) String email,
        @NotBlank(message = NO_PASSWORD) String password,
        @NotBlank(message = NO_USERNAME) String username,
        @NotBlank String studentId,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-M-d", timezone = "Asia/Seoul")
        @NotNull(message = NO_BIRTH_DATE_ERROR) LocalDate birthDate,
        @NotNull(message = NO_GENDER_ENUM_ERROR) Gender gender
) {
    public static final String NO_PASSWORD = "패스워드가 없습니다.";
    public static final String NO_USERNAME = "사용자 이름이 없습니다.";
    public static final String NO_EMAIL = "이메일이 없습니다.";
    public static final String INVALID_EMAIL_FORMAT = "이메일 형식이 올바르지 않습니다.";
    public static final String NO_BIRTH_DATE_ERROR = "birthDate 값이 없습니다.";
    public static final String NO_GENDER_ENUM_ERROR = "GENDER 값이 없습니다.";

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
