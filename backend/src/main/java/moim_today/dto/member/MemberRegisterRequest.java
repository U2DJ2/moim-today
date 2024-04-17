package moim_today.dto.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import moim_today.domain.member.MemberRegisterInfo;
import moim_today.domain.member.enums.Gender;

import java.time.LocalDate;

@Builder
public record MemberRegisterRequest (
        @NotNull long universityId,
        @NotNull long departmentId,
        @Email(message = INVALID_EMAIL_FORMAT) @NotBlank(message = NO_EMAIL) String email,
        @NotBlank(message = NO_PASSWORD) String password,
        @NotBlank(message = NO_USERNAME) String username,
        @NotBlank String studentId,
        @NotNull LocalDate birthDate,
        Gender gender
){
    private static final String NO_PASSWORD= "패스워드가 없습니다.";
    private static final String NO_USERNAME = "사용자 이름이 없습니다.";
    private static final String NO_EMAIL = "이메일이 없습니다.";
    private static final String INVALID_EMAIL_FORMAT = "이메일 형식이 올바르지 않습니다.";

    public MemberRegisterInfo toDomain(final String encodedPassword){
        return MemberRegisterInfo.builder()
                .universityId(universityId)
                .departmentId(departmentId)
                .email(email)
                .password(encodedPassword)
                .username(username)
                .studentId(studentId)
                .birthDate(birthDate)
                .gender(gender)
                .build();
    }
}
