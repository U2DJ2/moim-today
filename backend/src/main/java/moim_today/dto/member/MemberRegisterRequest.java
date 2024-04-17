package moim_today.dto.member;

import lombok.Builder;
import moim_today.domain.member.MemberRegisterInfo;
import moim_today.domain.member.enums.Gender;

import java.time.LocalDate;

@Builder
public record MemberRegisterRequest (
        long universityId,
        long departmentId,
        String email,
        String password,
        String username,
        String studentId,
        LocalDate birthDate,
        Gender gender
){

    public MemberRegisterInfo toDomain(){
        return MemberRegisterInfo.builder()
                .universityId(universityId)
                .departmentId(departmentId)
                .email(email)
                .password(password)
                .username(username)
                .studentId(studentId)
                .birthDate(birthDate)
                .gender(gender)
                .build();
    }
}
