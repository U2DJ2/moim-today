package moim_today.domain.member;

import lombok.Builder;
import moim_today.domain.member.enums.Gender;
import moim_today.persistence.entity.member.MemberJpaEntity;

import java.time.LocalDate;

import static moim_today.global.constant.MemberConstant.DEFAULT_PROFILE_URL;

@Builder
public record MemberRegisterInfo (
        long universityId,
        long departmentId,
        String email,
        String password,
        String username,
        String studentId,
        LocalDate birthDate,
        Gender gender
) {
    public MemberJpaEntity toEntity(){
        return MemberJpaEntity.builder()
                .universityId(universityId)
                .departmentId(departmentId)
                .email(email)
                .password(password)
                .username(username)
                .studentId(studentId)
                .birthDate(birthDate)
                .gender(gender)
                .memberProfileImageUrl(DEFAULT_PROFILE_URL.value())
                .build();
    }
}
