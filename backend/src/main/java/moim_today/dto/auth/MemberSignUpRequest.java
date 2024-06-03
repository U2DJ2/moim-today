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

@Builder
public record MemberSignUpRequest(
        @Min(value = 1, message = UNIVERSITY_ID_MIN_ERROR) long universityId,
        @Min(value = 1, message = DEPARTMENT_ID_MIN_ERROR) long departmentId,
        @Email(message = INVALID_EMAIL_FORMAT) @NotBlank(message = EMAIL_BLANK_ERROR) String email,
        @NotBlank(message = PASSWORD_BLANK_ERROR) String password,
        @NotBlank(message = USERNAME_BLANK_ERROR) String username,
        @NotBlank(message = STUDENT_ID_BLANK_ERROR) String studentId,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-M-d", timezone = "Asia/Seoul")
        @NotNull(message = BIRTH_DATE_NULL_ERROR) LocalDate birthDate,
        @NotNull(message = GENDER_NULL_ERROR) Gender gender
) {
    private static final String UNIVERSITY_ID_MIN_ERROR = "잘못된 대학 ID 값이 들어왔습니다.";
    private static final String DEPARTMENT_ID_MIN_ERROR = "잘못된 학과 ID 값이 들어왔습니다.";
    private static final String PASSWORD_BLANK_ERROR = "패스워드는 공백일 수 없습니다.";
    private static final String USERNAME_BLANK_ERROR = "사용자 이름은 공백일 수 없습니다.";
    private static final String STUDENT_ID_BLANK_ERROR = "학번은 공백일 수 없습니다.";
    private static final String EMAIL_BLANK_ERROR = "이메일은 공백일 수 없습니다.";
    private static final String INVALID_EMAIL_FORMAT = "이메일 형식이 올바르지 않습니다.";
    private static final String BIRTH_DATE_NULL_ERROR = "생일 값이 없습니다.";
    private static final String GENDER_NULL_ERROR = "성별 값이 없습니다.";

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
